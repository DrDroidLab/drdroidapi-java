package org.drdroid.api.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.drdroid.api.message.Data;
import org.drdroid.api.models.UUIDRegister;
import org.drdroid.api.models.ClientConfig;
import org.drdroid.api.IDrDroidAPI;
import org.drdroid.api.producer.IProducer;
import org.drdroid.api.utils.WorkflowEventDecorator;
import org.drdroid.api.models.WorkflowEvent;
import org.drdroid.api.producer.HTTPProducer;
import org.drdroid.api.utils.DateTimeFormatter;
import org.drdroid.api.utils.WorkflowEventTransformer;

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

    private Boolean registered = false;
    private final int eventLimit;
    private final int batchSize;
    private final int maxWaitTimeInMs;

    private final AtomicLong droppedCount = new AtomicLong(0L);
    private final AtomicLong eventId = new AtomicLong(0L);
    private final BlockingQueue<WorkflowEvent> events = new LinkedBlockingQueue<>();
    private final Lock registerLock = new ReentrantLock();

    private final WorkflowEventDecorator workflowEventDecorator;
    private final IProducer producer;
    private final ClientConfig clientConfig;

    public AsyncClient(ClientConfig config) {
        this.clientConfig = config;
        this.eventLimit = config.getMaxQueueSize();
        this.maxWaitTimeInMs = config.getAsyncMaxWaitTimeInMs();
        this.batchSize = config.getAsyncBatchSize();
        this.workflowEventDecorator = new WorkflowEventDecorator(config.getServiceName());
        this.producer = new HTTPProducer(clientConfig);
        this.createQueuePoller();
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
    public void send(String workflowName, String state, Map<String, ?> kvPairs) {
        String timestamp = DateTimeFormatter.getCurrentFormattedTimeStamp();
        WorkflowEvent event = WorkflowEventTransformer.transform(workflowName, state, kvPairs, timestamp);
        if (this.events.size() > this.eventLimit) {
            if (this.droppedCount.incrementAndGet() % 1000L == 0L) {
                System.out.printf("Dropped DrDroid event count has reached: %s", this.droppedCount.get());
            }
        } else {
            this.events.add(event);
        }
    }

    private void createQueuePoller() {
        int qps = clientConfig.getMessagePerSecond();
        float messageSentPerSecondInSingleThread = (float) (1000 * this.batchSize / clientConfig.getSocketTimeoutInMs());
        int threadsRequied = qps / (int) messageSentPerSecondInSingleThread;
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
                    //long startTime = System.currentTimeMillis();

                    List<WorkflowEvent> eventSet = new ArrayList<>();
                    AsyncClient.this.events.drainTo(eventSet, AsyncClient.this.batchSize);

                    if (!AsyncClient.this.registered) {
                        AsyncClient.this.register();
                    }

                    if (eventSet.size() > 0) {
                        List<WorkflowEvent> workflowEventSet = new ArrayList<>(eventSet.size());
                        Iterator<WorkflowEvent> var5 = eventSet.iterator();

                        while (true) {
                            if (!var5.hasNext()) {
                                AsyncClient.this.producer.sendBatch(new Data(workflowEventSet));
                                //long endTime = System.currentTimeMillis();
                                //AsyncClient.logger.debug("Total time taken is {}", endTime - startTime);
                                break;
                            }

                            WorkflowEvent event = var5.next();
                            long eventNum = AsyncClient.this.eventId.incrementAndGet();
                            workflowEventSet.add(AsyncClient.this.workflowEventDecorator.build(event, eventNum, uuid.toString()));
                        }
                    }

                    if (AsyncClient.this.events.size() < AsyncClient.this.eventLimit) {
                        Thread.sleep((long) AsyncClient.this.maxWaitTimeInMs);
                    }
                } catch (Exception ignored) {
                    //AsyncClient.logger.error("MessageConsumer poller exception: " + var9.getMessage(), var9);
                }
            }
        };
    }

    private void register() {
        Map<String, String> resourceKvs = new HashMap<>();
        resourceKvs.put("Port", String.valueOf(clientConfig.getServicePort()));
        if (this.registerLock.tryLock()) {
            try {
                UUIDRegister register = new UUIDRegister();
                register.setServiceName(clientConfig.getServiceName());
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
