package com.mike.moneyman.service.impl;

import com.mike.moneyman.dao.PipelineDAO;
import com.mike.moneyman.domain.Pipeline;
import com.mike.moneyman.service.PipelineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineServiceImpl implements PipelineService {
    private final PipelineDAO dao;

    @Autowired
    public PipelineServiceImpl(PipelineDAO dao) {
        this.dao = dao;
    }

    @Override
    public Pipeline save(Pipeline pipeline) {
        return dao.save(pipeline);
    }

    @Override
    public void update(Pipeline pipeline) {
        dao.update(pipeline.getName(),pipeline.getDescription(),pipeline.getStatus(),pipeline.getStartTime(),pipeline.getEndTime(),pipeline.getId());
    }

    @Override
    public Pipeline getById(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public List<Pipeline> getAll() {
        return (List<Pipeline>) dao.findAll();
    }

    @Override
    public void delete(Pipeline pipeline) {
        dao.delete(pipeline);
    }

    @Override
    public Pipeline getByName(String name) {
        return dao.findByName(name);
    }
}
