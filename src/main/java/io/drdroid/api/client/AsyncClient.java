package io.drdroid.api.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.drdroid.api.ClientRegistry;
import io.drdroid.api.ClientConfiguration;
import io.drdroid.api.models.IngestionEvent;
import io.drdroid.api.models.http.request.Data;
import io.drdroid.api.models.http.request.UUIDRegister;
import io.drdroid.api.producer.HTTPProducer;
import io.drdroid.api.utils.IngestionEventTransformer;

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
    private final BlockingQueue<IngestionEvent> events = new LinkedBlockingQueue<>();
    private final Lock registerLock = new ReentrantLock();

    private Boolean registered = false;

    private AsyncClient() {
        this.createQueuePoller();
    }

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
    public void send(String eventName, Map<String, ?> kvs, long timestamp) {
        IngestionEvent ingestionEvent = IngestionEventTransformer.transform(eventName, kvs, timestamp);
        if (this.events.size() > ClientConfiguration.getMaxQueueSize()) {
            this.droppedCount.incrementAndGet();
        } else {
            this.events.add(ingestionEvent);
        }
    }

    private void createQueuePoller() {
        float messageSentPerSecondInSingleThread = (float) (1000 * ClientConfiguration.getAsyncBatchSize() / ClientConfiguration.getSocketTimeoutInMs());
        int threadsRequied = ClientConfiguration.getMessagePerSecond() / (int) messageSentPerSecondInSingleThread;
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
                    List<IngestionEvent> eventSet = new ArrayList<>();
                    AsyncClient.this.events.drainTo(eventSet, ClientConfiguration.getAsyncBatchSize());

                    if (!AsyncClient.this.registered) {
                        AsyncClient.this.register();
                    }

                    if (eventSet.size() > 0) {
                        List<IngestionEvent> workflowIngestionEventSet = new ArrayList<>(eventSet.size());
                        Iterator<IngestionEvent> var5 = eventSet.iterator();

                        while (true) {
                            if (!var5.hasNext()) {
                                HTTPProducer.getHTTPProducer().sendBatch(new Data(workflowIngestionEventSet));
                                break;
                            }

                            long eventNum = AsyncClient.this.eventId.incrementAndGet();
                            workflowIngestionEventSet.add(var5.next());
                        }
                    }

                    if (AsyncClient.this.events.size() < ClientConfiguration.getMaxQueueSize()) {
                        Thread.sleep(ClientConfiguration.getAsyncMaxWaitTimeInMs());
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
                register.setServiceName(ClientRegistry.getServiceName());
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
