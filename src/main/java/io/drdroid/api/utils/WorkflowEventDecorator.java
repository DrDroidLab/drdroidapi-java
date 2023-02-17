package io.drdroid.api.utils;

import io.drdroid.api.Configuration;
import io.drdroid.api.models.value.KeyValue;
import io.drdroid.api.models.value.Value;
import io.drdroid.api.models.WorkflowEvent;

import java.util.*;

public class WorkflowEventDecorator {

    private static final String drdEvIdKey = "$drd_ev_id";
    private static final String serviceNameKey = "$drd_service_name";
    private static final String drdAgentIdKey = "$drd_agent_id";

    public static WorkflowEvent build(WorkflowEvent workflowEvent, long eventId, String agentId) {
        List<KeyValue> kvs = workflowEvent.getKvs();

        if (Objects.isNull(kvs) || kvs.isEmpty()) {
            kvs = new ArrayList<>();
        }

        Value drdEvIdValue = new Value();
        drdEvIdValue.setLongValue(eventId);
        drdEvIdValue.setValid(true);
        kvs.add(new KeyValue(drdEvIdKey, drdEvIdValue));

        Value drdAgentIdValue = new Value();
        drdAgentIdValue.setStringValue(agentId);
        drdAgentIdValue.setValid(true);
        kvs.add(new KeyValue(drdAgentIdKey, drdAgentIdValue));

        Value serviceNameValue = new Value();
        serviceNameValue.setStringValue(Configuration.getServiceName());
        serviceNameValue.setValid(true);
        kvs.add(new KeyValue(serviceNameKey, serviceNameValue));

        return workflowEvent;
    }

}
