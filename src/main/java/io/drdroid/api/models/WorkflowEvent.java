package io.drdroid.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowEvent implements java.io.Serializable {

    private Workflow workflow;
    private String timestamp;
    private String state;
    private Map<String, Object> payload;

    public WorkflowEvent() {
    }

    public WorkflowEvent(Workflow workflow, String timestamp, String state, Map<String, Object> payload) {
        this.workflow = workflow;
        this.timestamp = timestamp;
        this.state = state;
        this.payload = payload;
    }

    @JsonGetter("workflow")
    public Workflow getWorkflow() {
        return this.workflow;
    }

    @JsonSetter("workflow")
    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    @JsonGetter("timestamp")
    public String getTimestamp() {
        return this.timestamp;
    }

    @JsonSetter("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonGetter("state")
    public String getState() {
        return this.state;
    }

    @JsonSetter("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonGetter("payload")
    public Map<String, Object> getPayload() {
        return this.payload;
    }

    @JsonSetter("payload")
    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

}
