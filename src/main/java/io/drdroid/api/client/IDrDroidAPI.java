package io.drdroid.api.client;

import java.util.Map;

public interface IDrDroidAPI {

    void send(String workflowName, String state, Map<String, ?> kvs);

    long getSentEventCount();

    long getLostEventCount();

    int getNumOfPendingEvents();

}
