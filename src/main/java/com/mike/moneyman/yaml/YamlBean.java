package com.mike.moneyman.yaml;

import com.mike.moneyman.domain.Task;
import com.mike.moneyman.yaml.YamlTransitionBean;

import java.io.Serializable;
import java.util.List;

public class YamlBean implements Serializable{

    private String name;

    private String description;

    private List<Task> tasks;

    private List<YamlTransitionBean> transitions;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<YamlTransitionBean> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<YamlTransitionBean> transitions) {
        this.transitions = transitions;
    }

    @Override
    public String toString() {
        return "YamlBean{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                ", transitions=" + transitions +
                '}';
    }
}
