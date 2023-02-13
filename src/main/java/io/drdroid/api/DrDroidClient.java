package io.drdroid.api;

import io.drdroid.api.client.AsyncClient;
import io.drdroid.api.models.ClientConfig;

import java.util.Map;

public class DrDroidClient implements IDrDroidAPI {

    private final IDrDroidAPI client;

    public DrDroidClient(ClientConfig clientConfig) {
        client = new AsyncClient(clientConfig);
    }

    @Override
    public void send(String workflowName, String state, Map<String, Object> payload) {
        this.client.send(workflowName, state, payload);
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