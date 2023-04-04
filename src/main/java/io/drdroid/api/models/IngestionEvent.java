package io.drdroid.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.drdroid.api.models.kvs.KeyValue;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngestionEvent implements java.io.Serializable {

    private String name;
    private List<KeyValue> kvs;
    private long timestamp;

    public IngestionEvent() {
    }

    public IngestionEvent(String name, List<KeyValue> kvs, long timestamp) {
        this.name = name;
        this.kvs = kvs;
        this.timestamp = timestamp;
    }

    @JsonGetter("name")
    public String getName() {
        return this.name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("kvs")
    public List<KeyValue> getKvs() {
        return this.kvs;
    }

    @JsonSetter("kvs")
    public void setKvs(List<KeyValue> kvs) {
        this.kvs = kvs;
    }

    @JsonGetter("timestamp")
    public long getTimestamp() {
        return this.timestamp;
    }

    @JsonSetter("timestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
