package com.mike.moneyman.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YamlTransitionBean {
    @JsonProperty("task")
    private String task;
    @JsonProperty("transition")
    private String transition;


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }
}
