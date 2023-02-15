package io.drdroid.api.utils;

import io.drdroid.api.Configuration;
import io.drdroid.api.models.WorkflowEvent;

import java.util.*;

public class WorkflowEventDecorator {

    private static final String drdEvIdKey = "$drd_ev_id";
    private static final String serviceNameKey = "$drd_service_name";
    private static final String drdAgentIdKey = "$drd_agent_id";

    public static WorkflowEvent build(WorkflowEvent workflowEvent, long eventId, String agentId) {
        Map<String, Object> payload = workflowEvent.getPayload();

        if (Objects.isNull(payload) || payload.isEmpty()) {
            payload = new HashMap<>();
        }

        payload.put(drdEvIdKey, eventId);
        payload.put(drdAgentIdKey, agentId);
        payload.put(serviceNameKey, Configuration.getServiceName());

        return workflowEvent;
    }

}
