package org.drdroid.api.producer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.drdroid.api.message.Data;

public class Payload implements java.io.Serializable {

    private Data data;

    public Payload() {
    }

    public Payload(Data data) {
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
