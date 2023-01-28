package io.drdroid.api.utils;

import io.drdroid.api.models.KeyValue;
import io.drdroid.api.models.Value;
import io.drdroid.api.models.WorkflowEvent;

import java.util.*;

public class WorkflowEventDecorator {

    private static final String drdEvIdKey = "$drd_ev_id";
    private static final String serviceNameKey = "service";

    private static final String drdAgentIdKey = "$drd_agent_id";

    private final String service;

    public WorkflowEventDecorator(String serviceNameValue) {
        this.service = serviceNameValue;
    }

    public WorkflowEvent build(WorkflowEvent workflowEvent, long eventId, String agentId) {
        List<KeyValue> kvPairs = workflowEvent.getKvPairs();

        if (Objects.isNull(kvPairs)) {
            kvPairs = new ArrayList<>();
        }

        Value drdEvIdValue = new Value();
        drdEvIdValue.setLongValue(eventId);
        drdEvIdValue.setValid(true);
        kvPairs.add(new KeyValue(drdEvIdKey, drdEvIdValue));

        Value drdAgentIdValue = new Value();
        drdAgentIdValue.setStringValue(agentId);
        drdAgentIdValue.setValid(true);
        kvPairs.add(new KeyValue(drdAgentIdKey, drdAgentIdValue));

        Value serviceNameValue = new Value();
        serviceNameValue.setStringValue(service);
        serviceNameValue.setValid(true);
        kvPairs.add(new KeyValue(serviceNameKey, serviceNameValue));

        return workflowEvent;
    }

}
