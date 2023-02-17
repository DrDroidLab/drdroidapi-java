package io.drdroid.api.models.value;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Value {

    private String stringValue;
    private Boolean booleanValue;
    private Long longValue;
    private Double doubleValue;
    private Byte byteValue;
    private ArrayValues arrayValue;
    private KvValues keyValueList;

    public static Value newStringValue(String value) {
        Value typedValue = new Value();
        typedValue.setStringValue(value);
        typedValue.setValid(true);
        return typedValue;
    }

    public static Value newBooleanValue(Boolean value) {
        Value typedValue = new Value();
        typedValue.setBooleanValue(value);
        typedValue.setValid(true);
        return typedValue;
    }

    public static Value newLongValue(Long value) {
        Value typedValue = new Value();
        typedValue.setLongValue(value);
        typedValue.setValid(true);
        return typedValue;
    }

    public static Value newDoubleValue(Double value) {
        Value typedValue = new Value();
        typedValue.setDoubleValue(value);
        typedValue.setValid(true);
        return typedValue;
    }

    public static Value newArrayValue(List<Value> value) {
        Value typedValue = new Value();
        typedValue.setArrayValue(value);
        typedValue.setValid(true);
        return typedValue;
    }

    public static Value newKvValueList(List<KeyValue> value) {
        Value typedValue = new Value();
        typedValue.setKeyValueList(value);
        typedValue.setValid(true);
        return typedValue;
    }

    @JsonIgnore
    boolean valid = false;

    public Value() {
    }

    @JsonGetter("string_value")
    public String getStringValue() {
        return this.stringValue;
    }

    @JsonSetter("string_value")
    public void setStringValue(String value) {
        this.stringValue = value;
    }

    @JsonGetter("bool_value")
    public Boolean getBooleanValue() {
        return this.booleanValue;
    }

    @JsonSetter("bool_value")
    public void setBooleanValue(Boolean value) {
        this.booleanValue = value;
    }

    @JsonGetter("int_value")
    public Long getLongValue() {
        return this.longValue;
    }

    @JsonSetter("int_value")
    public void setLongValue(Long value) {
        this.longValue = value;
    }

    @JsonGetter("double_value")
    public Double getDoubleValue() {
        return this.doubleValue;
    }

    @JsonSetter("double_value")
    public void setDoubleValue(Double value) {
        this.doubleValue = value;
    }

    @JsonGetter("array_value")
    public ArrayValues getArrayValue() {
        return arrayValue;
    }

    @JsonSetter("array_value")
    public void setArrayValue(List<Value> arrayValue) {
        this.arrayValue = new ArrayValues(arrayValue);
    }

    @JsonGetter("kvlist_value")
    public KvValues getKeyValueList() {
        return keyValueList;
    }

    @JsonSetter("kvlist_value")
    public void setKeyValueList(List<KeyValue> keyValueList) {
        this.keyValueList = new KvValues(keyValueList);
    }

    @JsonGetter("bytes_value")
    public Byte getByteValue() {
        return this.byteValue;
    }

    @JsonSetter("bytes_value")
    public void setByteValue(Byte value) {
        this.byteValue = value;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }

}