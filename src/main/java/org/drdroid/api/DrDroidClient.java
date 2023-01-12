package org.drdroid.api;

import org.drdroid.api.client.AsyncClient;
import org.drdroid.api.models.ClientConfig;

import java.util.Map;

public class DrDroidClient implements IDrDroidAPI {

    private IDrDroidAPI client;

    /**
     * Default Constructor
     */
    public DrDroidClient() {
    }

    public DrDroidClient(ClientConfig clientConfig) {
        client = new AsyncClient(clientConfig);
    }

    @Override
    public void send(String workflowName, String state, Map<String, Object> kvPairs) {
        this.client.send(workflowName, state, kvPairs);
    }

    @Override
    public long getSentEventCount() {
        return this.client.getSentEventCount();
    }

    @Override
    public long getLostEventCount() {
        return this.client.getLostEventCount();
    }

    @Override
    public int getNumOfPendingEvents() {
        return this.client.getNumOfPendingEvents();
    }

}
