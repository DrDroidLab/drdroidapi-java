package io.drdroid.api.utils;

import io.drdroid.api.models.*;
import io.drdroid.api.models.kvs.KeyValue;
import io.drdroid.api.models.kvs.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkflowEventTransformer {

    public static WorkflowEvent transform(String workflowName, String state, Map<String, ?> kvPairs, String timeStamp) {
        List<KeyValue> modelKvPairs = getKvs(kvPairs);
        Workflow workflow = new Workflow(workflowName);
        return new WorkflowEvent(workflow, timeStamp, state, modelKvPairs);
    }

    private static List<KeyValue> getKvs(Map<String, ?> input) {
        List<KeyValue> generatedPairs = new ArrayList<>();
        input.entrySet().forEach(entry -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                generatedPairs.add(new KeyValue(key, Value.newKvValueList(getKvs((Map<String, ?>) value))));
            } else if (value instanceof List) {
                generatedPairs.add(new KeyValue(key, Value.newArrayValue(getArrayValue(value))));
            } else {
                generatedPairs.add(new KeyValue(key, getPrimitiveValue(value)));
            }
        });
        return generatedPairs;
    }

    private static List<Value> getArrayValue(Object value) {
        List<Value> typedValues = new ArrayList<>();
        ((List<?>) value).forEach(v -> {
            if (v instanceof List) {
                typedValues.addAll(getArrayValue(v));
            } else if (v instanceof Map) {
              typedValues.add(Value.newKvValueList(getKvs((Map<String, ?>) v)));
            } else {
                typedValues.add(getPrimitiveValue(v));
            }
        });
        return typedValues;
    }

    private static Value getPrimitiveValue(Object value) {
        if (value instanceof String) {
            return Value.newStringValue((String) value);
        } else if (value instanceof Boolean) {
            return Value.newBooleanValue((Boolean) value);
        } else if (value instanceof Integer) {
            return Value.newLongValue(Long.valueOf((Integer) value));
        } else if (value instanceof Long) {
            return Value.newLongValue((Long) value);
        } else if (value instanceof Float) {
            return Value.newDoubleValue(Double.valueOf((Float) value));
        } else if (value instanceof Double) {
            return Value.newDoubleValue((Double) value);
        } else {
            return Value.newKvValueList(getKvs(ObjectToMapTemplate.convertObjectToMap(value)));
        }
    }

}
