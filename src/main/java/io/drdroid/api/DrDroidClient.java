package io.drdroid.api;

import io.drdroid.api.client.AsyncClient;
import io.drdroid.api.client.IDrDroidAPI;

import java.util.Map;

public class DrDroidClient {

    private static final Object syncObject = new Object();
    private static DrDroidClient instance = null;

    private final IDrDroidAPI client;

    private DrDroidClient() {
        client = AsyncClient.getAsyncClientInstance();
    }

    private static DrDroidClient getDrDroidClient() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new DrDroidClient();
            }
        }
        return instance;
    }

    public static void initDrDroidClient(String org, String sinkUrl, String serviceName) {
        synchronized (syncObject) {
            if (null == instance) {
                Configuration.org = org;
                Configuration.sinkUrl = sinkUrl;
                Configuration.serviceName = serviceName;
                instance = getDrDroidClient();
            }
        }
    }

    public static void send(String workflowName, String state, Map<String, Object> payload) {
        getDrDroidClient().client.send(workflowName, state, payload);
    }

    public static long getSentEventCount() {
        return getDrDroidClient().client.getSentEventCount();
    }

    public static long getLostEventCount() {
        return getDrDroidClient().client.getLostEventCount();
    }

    public static int getNumOfPendingEvents() {
        return getDrDroidClient().client.getNumOfPendingEvents();
    }

}
