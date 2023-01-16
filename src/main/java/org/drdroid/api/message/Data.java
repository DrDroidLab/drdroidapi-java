package org.drdroid.api.message;

import com.fasterxml.jackson.annotation.*;
import org.drdroid.api.models.WorkflowEvent;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data implements java.io.Serializable {

    List<WorkflowEvent> events;

    public Data() {
    }

    public Data(List<WorkflowEvent> events) {
        this.events = events;
    }

    @JsonGetter("events")
    public List<WorkflowEvent> getEvents() {
        return events;
    }

    @JsonSetter("events")
    public void setEvents(List<WorkflowEvent> events) {
        this.events = events;
    }

}
