package io.drdroid.api.models.http.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class RequestPayload implements java.io.Serializable {

    private Data data;

    public RequestPayload() {
    }

    public RequestPayload(Data data) {
        this.data = data;
    }

    @JsonGetter("data")
    public Data getData() {
        return data;
    }

    @JsonSetter("data")
    public void setData(Data data) {
        this.data = data;
    }

}
