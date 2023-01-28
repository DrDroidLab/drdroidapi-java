package io.drdroid.api;

import java.util.Map;

public interface IDrDroidAPI {

    void send(String workflowName, String state, Map<String, ?> kvPairs);

    public long getSentEventCount();

    public long getLostEventCount();

    public int getNumOfPendingEvents();

}
