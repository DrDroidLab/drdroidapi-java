package io.drdroid.api.models.kvs;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KvValues {

    private List<KeyValue> kvValues;

    public KvValues(List<KeyValue> kvValues) {
        this.kvValues = kvValues;
    }

    @JsonGetter("values")
    public List<KeyValue> getKvValues() {
        return this.kvValues;
    }

    @JsonSetter("values")
    public void setKvValues(List<KeyValue> kvValues) {
        this.kvValues = kvValues;
    }

}
