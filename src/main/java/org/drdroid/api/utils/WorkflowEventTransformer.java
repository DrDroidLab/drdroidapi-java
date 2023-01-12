package org.drdroid.api.utils;

import org.drdroid.api.models.Value;
import org.drdroid.api.models.Workflow;
import org.drdroid.api.models.WorkflowEvent;

import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowEventTransformer {

    public static WorkflowEvent transform(String workflowName, String state, Map<String, Object> kvPairs, String timeStamp) {
        Map<String, Value> modelKvPairs = kvPairs.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, WorkflowEventTransformer::getValueWithType));

        Workflow workflow = new Workflow(workflowName);
        return new WorkflowEvent(workflow, timeStamp, state, modelKvPairs);
    }

    public static Value getValueWithType(Object value) {

        Value typedValue = new Value();
        if (value instanceof String) {
            typedValue.setStringValue(value.toString());
        } else if (value instanceof Boolean) {
            typedValue.setBooleanValue((Boolean) value);
        } else if (value instanceof Integer) {
            typedValue.setLongValue(Long.valueOf((Integer) value));
        } else if (value instanceof Long) {
            typedValue.setLongValue((Long) value);
        } else if (value instanceof Double) {
            typedValue.setDoubleValue((Double) value);
        } else if (value instanceof Byte) {
            typedValue.setByteValue((Byte) value);
        }
        return typedValue;

    }

}
