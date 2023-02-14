package io.drdroid.api.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.drdroid.api.Configuration;
import io.drdroid.api.models.ClientConfig;
import io.drdroid.api.models.Workflow;
import io.drdroid.api.producer.IProducer;
import io.drdroid.api.utils.WorkflowEventDecorator;
import io.drdroid.api.models.http.request.Data;
import io.drdroid.api.models.http.request.UUIDRegister;
import io.drdroid.api.models.WorkflowEvent;
import io.drdroid.api.producer.HTTPProducer;
import io.drdroid.api.utils.DateTimeFormatter;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsyncClient implements IDrDroidAPI {

    private static final int MAX_THREADS = 40;
    private static final int MIN_THREADS = 1;
    private static final UUID uuid = UUID.randomUUID();
    private static final Object clientSync = new Object();
    private static AsyncClient instance = null;

    private final AtomicLong droppedCount = new AtomicLong(0L);
    private final AtomicLong eventId = new AtomicLong(0L);
    private final BlockingQueue<WorkflowEvent> events = new LinkedBlockingQueue<>();
    private final Lock registerLock = new ReentrantLock();

    private Boolean registered = false;

    private AsyncClient() {
        this.createQueuePoller();
    }

    /**
     * Singleton pattern implementation
     * @return The singleton instance of the AsyncClient class
     */
    public static IDrDroidAPI getAsyncClientInstance() {
        synchronized (clientSync) {
            if (null == instance) {
                instance = new AsyncClient();
            }
        }
        return instance;
    }

    @Override
    public long getSentEventCount() {
        return this.eventId.get();
    }

    @Override
    public long getLostEventCount() {
        return this.droppedCount.get();
    }

    @Override
    public int getNumOfPendingEvents() {
        return this.events.size();
    }

    @Override
    public void send(String workflowName, String state, Map<String, Object> payload) {
        String timestamp = DateTimeFormatter.getCurrentFormattedTimeStamp();
        Workflow workflow = new Workflow(workflowName);
        WorkflowEvent event = new WorkflowEvent(workflow, timestamp, state, payload);
        if (this.events.size() > ClientConfig.maxQueueSize) {
            this.droppedCount.incrementAndGet();
        } else {
            this.events.add(event);
        }
    }

    private void createQueuePoller() {
        float messageSentPerSecondInSingleThread = (float) (1000 * ClientConfig.asyncBatchSize / ClientConfig.socketTimeoutInMs);
        int threadsRequied = ClientConfig.messagePerSecond / (int) messageSentPerSecondInSingleThread;
        if (threadsRequied > MAX_THREADS) {
            threadsRequied = MAX_THREADS;
        } else if (threadsRequied < MIN_THREADS) {
            threadsRequied = MIN_THREADS;
        }

        ExecutorService poller = Executors.newFixedThreadPool(threadsRequied, (new ThreadFactoryBuilder()).setNameFormat("AsyncDrDroidClientPoller-%d").build());

        for (int i = 0; i < threadsRequied; ++i) {
            poller.execute(this.createPoller());
        }
    }

    private Runnable createPoller() {
        return () -> {
            while (true) {
                try {
                    List<WorkflowEvent> eventSet = new ArrayList<>();
                    AsyncClient.this.events.drainTo(eventSet, ClientConfig.asyncBatchSize);

                    if (!AsyncClient.this.registered) {
                        AsyncClient.this.register();
                    }

                    if (eventSet.size() > 0) {
                        List<WorkflowEvent> workflowEventSet = new ArrayList<>(eventSet.size());
                        Iterator<WorkflowEvent> var5 = eventSet.iterator();

                        while (true) {
                            if (!var5.hasNext()) {
                                HTTPProducer.getHTTPProducer().sendBatch(new Data(workflowEventSet));
                                break;
                            }

                            WorkflowEvent event = var5.next();
                            long eventNum = AsyncClient.this.eventId.incrementAndGet();
                            workflowEventSet.add(WorkflowEventDecorator.build(event, eventNum, uuid.toString()));
                        }
                    }

                    if (AsyncClient.this.events.size() < ClientConfig.maxQueueSize) {
                        Thread.sleep(ClientConfig.asyncMaxWaitTimeInMs);
                    }
                } catch (Exception ignored) {
                }
            }
        };
    }

    @VisibleForTesting
    protected void register() {
        Map<String, String> resourceKvs = new HashMap<>();
        if (this.registerLock.tryLock()) {
            try {
                UUIDRegister register = new UUIDRegister();
                register.setServiceName(Configuration.serviceName);
                register.setUuid(uuid);
                register.setResourceKvs(resourceKvs);
                register.setIp(InetAddress.getLocalHost().getHostAddress());
                this.registered = true;
                //this.registered = this.producer.register(register);
            } catch (Exception ignored) {
            }
        }
    }

}
