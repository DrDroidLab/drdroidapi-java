package io.drdroid.api.client;

import java.util.Map;

public interface IDrDroidAPI {

    void send(String eventName, Map<String, ?> kvs, long timestamp);

    long getSentEventCount();

    long getLostEventCount();

    int getNumOfPendingEvents();

}
