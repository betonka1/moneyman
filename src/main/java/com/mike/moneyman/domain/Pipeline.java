package com.mike.moneyman.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pipelines", schema = "moneyman", catalog = "postgres")
public class Pipeline {
    private Long id;
    @JsonProperty
    private String name;
    private String status = "PENDING";
    private String startTime;
    private String endTime;
    @JsonProperty
    private String description;
    @JsonProperty
    @JsonManagedReference
    private List<Task> tasks;

    public Pipeline() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "execution_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "start_time", nullable = true, length = 100)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true, length = 100)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status", length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 40)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "pipeline", fetch = FetchType.EAGER)
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = true)
//    @JoinColumn(name = "execution")
//    public Execution getExecution() {
//        return execution;
//    }
//
//    public void setExecution(Execution execution) {
//        this.execution = execution;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pipeline pipeline = (Pipeline) o;
        return Objects.equals(id, pipeline.id) &&
                Objects.equals(name, pipeline.name) &&
                Objects.equals(status, pipeline.status) &&
                Objects.equals(startTime, pipeline.startTime) &&
                Objects.equals(endTime, pipeline.endTime) &&
                Objects.equals(description, pipeline.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, startTime, endTime, description);
    }

    @Override
    public String toString() {
        return "Pipeline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
