package io.drdroid.api.models.kvs;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyValue {

    private String key;
    private Value value;

    public KeyValue() {
    }

    public KeyValue(String key, Value value) {
        this.key = key;
        this.value = value;
    }

    @JsonGetter("key")
    public String getKey() {
        return key;
    }

    @JsonSetter("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonGetter("value")
    public Value getValue() {
        return value;
    }

    @JsonSetter("value")
    public void setValue(Value value) {
        this.value = value;
    }

}