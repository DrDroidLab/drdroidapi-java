package io.drdroid.api.models.http.request;

import com.fasterxml.jackson.annotation.*;
import io.drdroid.api.models.IngestionEvent;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data implements java.io.Serializable {

    List<IngestionEvent> ingestionEvents;

    public Data() {
    }

    public Data(List<IngestionEvent> ingestionEvents) {
        this.ingestionEvents = ingestionEvents;
    }

    @JsonGetter("events")
    public List<IngestionEvent> getEvents() {
        return ingestionEvents;
    }

    @JsonSetter("events")
    public void setEvents(List<IngestionEvent> ingestionEvents) {
        this.ingestionEvents = ingestionEvents;
    }

}
