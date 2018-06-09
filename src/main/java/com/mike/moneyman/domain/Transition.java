package com.mike.moneyman.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "transitions", schema = "moneyman", catalog = "postgres")
public class Transition implements Serializable {

    private Long id;
    @JsonBackReference
    private Task task;
    private String transition;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task")
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    @Basic
    @Column(name = "transition")
    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transition, that.transition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transition);
    }

    @Override
    public String toString() {
        return "Transition{" +
                "id=" + id +

                ", transition='" + transition + '\'' +
                '}';
    }
}
