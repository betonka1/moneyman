package com.mike.moneyman.service;


import com.mike.moneyman.domain.Pipeline;

import java.util.List;

public interface PipelineService {

    Pipeline save(Pipeline pipeline);
    void update(Pipeline pipeline);
    Pipeline getById(Long id);
    List<Pipeline> getAll();
    void delete(Pipeline pipeline);
    Pipeline getByName(String name);


}
