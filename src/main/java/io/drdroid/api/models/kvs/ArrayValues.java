package io.drdroid.api.models.kvs;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrayValues {

    private List<Value> arrayValues;

    public ArrayValues(List<Value> arrayValues) {
        this.arrayValues = arrayValues;
    }

    @JsonGetter("values")
    public List<Value> getArrayValues() {
        return this.arrayValues;
    }

    @JsonSetter("values")
    public void setArrayValues(List<Value> arrayValues) {
        this.arrayValues = arrayValues;
    }

}
