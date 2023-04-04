package io.drdroid.api.utils;

import io.drdroid.api.Configuration;
import io.drdroid.api.models.kvs.KeyValue;
import io.drdroid.api.models.kvs.Value;
import io.drdroid.api.models.IngestionEvent;

import java.util.*;

public class IngestionEventDecorator {

    private static final String drdEvIdKey = "$drd_ev_id";
    private static final String serviceNameKey = "$drd_service_name";
    private static final String drdAgentIdKey = "$drd_agent_id";

    public static IngestionEvent build(IngestionEvent ingestionEvent, long eventId, String agentId) {
        List<KeyValue> kvs = ingestionEvent.getKvs();

        if (Objects.isNull(kvs) || kvs.isEmpty()) {
            kvs = new ArrayList<>();
        }

        kvs.add(new KeyValue(drdEvIdKey, Value.newLongValue(eventId)));
        kvs.add(new KeyValue(drdAgentIdKey, Value.newStringValue(agentId)));
        kvs.add(new KeyValue(serviceNameKey, Value.newStringValue(Configuration.getServiceName())));

        return ingestionEvent;
    }

}
