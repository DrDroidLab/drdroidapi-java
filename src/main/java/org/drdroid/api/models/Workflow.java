package org.drdroid.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Workflow {

    String name;

    public Workflow() {
    }

    public Workflow(String name) {
        this.name = name;
    }

    @JsonGetter("name")
    public String getName() {
        return this.name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

}
