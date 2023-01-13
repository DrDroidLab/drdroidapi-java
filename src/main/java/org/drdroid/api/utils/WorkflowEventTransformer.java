package org.drdroid.api.utils;

import org.drdroid.api.models.KeyValue;
import org.drdroid.api.models.Value;
import org.drdroid.api.models.Workflow;
import org.drdroid.api.models.WorkflowEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowEventTransformer {

    public static WorkflowEvent transform(String workflowName, String state, Map<String, ?> kvPairs, String timeStamp) {
        List<KeyValue> modelKvPairs = kvPairs.entrySet()
                .stream()
                .map(e -> new KeyValue(e.getKey(), getValueWithType(e.getValue())))
                .collect(Collectors.toList());

        modelKvPairs = modelKvPairs.stream().filter(kv -> kv.getValue().getValid()).collect(Collectors.toList());

        Workflow workflow = new Workflow(workflowName);
        return new WorkflowEvent(workflow, timeStamp, state, modelKvPairs);
    }

    private static Value getValueWithType(Object value) {

        Value typedValue = new Value();
        if (value instanceof String) {
            typedValue.setStringValue(value.toString());
            typedValue.setValid(true);
        } else if (value instanceof Boolean) {
            typedValue.setBooleanValue((Boolean) value);
            typedValue.setValid(true);
        } else if (value instanceof Integer) {
            typedValue.setLongValue(Long.valueOf((Integer) value));
            typedValue.setValid(true);
        } else if (value instanceof Long) {
            typedValue.setLongValue((Long) value);
            typedValue.setValid(true);
        } else if (value instanceof Double) {
            typedValue.setDoubleValue((Double) value);
            typedValue.setValid(true);
        } else if (value instanceof Byte) {
            typedValue.setByteValue((Byte) value);
            typedValue.setValid(true);
        } else {
            System.out.println("Encountered unexpected value type");
            typedValue.setValid(false);
        }
        return typedValue;
    }

}
