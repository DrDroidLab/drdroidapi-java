package io.drdroid.api.models;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Value {

    String stringValue;
    Boolean booleanValue;
    Long longValue;
    Double doubleValue;
    Byte byteValue;

    @JsonIgnore
    boolean valid = true;

    public Value() {
    }

    public Value(String stringValue, Boolean booleanValue, Long longValue, Double doubleValue, Byte byteValue, Boolean valid) {
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.longValue = longValue;
        this.byteValue = byteValue;
        this.valid = valid;
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
