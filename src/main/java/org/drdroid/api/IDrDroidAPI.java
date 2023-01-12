package org.drdroid.api;

import java.util.Map;

public interface IDrDroidAPI {

    void send(String workflowName, String state, Map<String, Object> kvPairs);

    public long getSentEventCount();

    public long getLostEventCount();

    public int getNumOfPendingEvents();

}
