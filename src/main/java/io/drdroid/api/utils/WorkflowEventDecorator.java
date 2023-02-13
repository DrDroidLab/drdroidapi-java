package io.drdroid.api.utils;

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
        Map<String, Object> payload = workflowEvent.getPayload();

        if (Objects.isNull(payload) || payload.isEmpty()) {
            payload = new HashMap<>();
        }

        payload.put(drdEvIdKey, eventId);
        payload.put(drdAgentIdKey, agentId);
        payload.put(serviceNameKey, service);

        return workflowEvent;
    }

}
