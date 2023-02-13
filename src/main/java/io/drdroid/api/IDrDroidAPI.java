package io.drdroid.api;

import java.util.Map;

public interface IDrDroidAPI {

    void send(String workflowName, String state, Map<String, Object> payload);

    public long getSentEventCount();

    public long getLostEventCount();

    public int getNumOfPendingEvents();

}
