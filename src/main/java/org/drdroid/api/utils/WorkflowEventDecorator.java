package org.drdroid.api.utils;

import org.drdroid.api.models.Value;
import org.drdroid.api.models.WorkflowEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorkflowEventDecorator {

    private static final String drdEvIdKey = "$drd_ev_id";
    private static final String serviceNameKey = "service";

    private static final String drdAgentIdKey = "$drd_agent_id";

    private final String service;

    public WorkflowEventDecorator(String serviceNameValue) {
        this.service = serviceNameValue;
    }

    public WorkflowEvent build(WorkflowEvent workflowEvent, long eventId, String agentId) {
        Map<String, Value> kvPairs = workflowEvent.getKvPairs();
        if (Objects.isNull(kvPairs)) {
            kvPairs = new HashMap<>();
        }
        Value drdEvIdValue = new Value();
        drdEvIdValue.setLongValue(eventId);
        kvPairs.put(drdEvIdKey, drdEvIdValue);

        Value drdAgentIdValue = new Value();
        drdAgentIdValue.setStringValue(agentId);
        kvPairs.put(drdAgentIdKey, drdAgentIdValue);

        Value serviceNameValue = new Value();
        serviceNameValue.setStringValue(service);
        kvPairs.put(serviceNameKey, serviceNameValue);

        return workflowEvent;
    }

}
