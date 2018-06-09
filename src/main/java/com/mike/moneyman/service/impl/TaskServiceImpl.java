package com.mike.moneyman.service.impl;

import com.mike.moneyman.dao.TaskDAO;
import com.mike.moneyman.domain.Task;
import com.mike.moneyman.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskDAO dao;

    @Autowired
    public TaskServiceImpl(TaskDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(Iterable<Task> task) {
        dao.saveAll(task);
    }

    @Override
    public void update(Task task) {
        dao.update(task.getStatus(),task.getStartTime(),task.getEndTime(),task.getId());
    }


    @Override
    public void delete(Task task) {
        dao.delete(task);
    }

    @Override
    public Task getById(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Task getByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Task> getAll() {
        return (List<Task>) dao.findAll();
    }
}
