package com.mike.moneyman.service;

import com.mike.moneyman.domain.Task;

import java.util.List;

public interface TaskService {

    void save(Iterable<Task> task);
    void update(Task task);
    void delete(Task task);
    Task getById(Long id);
    Task getByName(String name);
    List<Task> getAll();

}
