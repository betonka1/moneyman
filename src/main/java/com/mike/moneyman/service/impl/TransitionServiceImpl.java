package com.mike.moneyman.service.impl;

import com.mike.moneyman.dao.TransitionDAO;
import com.mike.moneyman.domain.Transition;
import com.mike.moneyman.service.TransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransitionServiceImpl implements TransitionService {
    private final TransitionDAO dao;

    @Autowired
    public TransitionServiceImpl(TransitionDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(Iterable<Transition> transition) {
        dao.saveAll(transition);
    }

    @Override
    public Integer count(String name) {
        return dao.countByTransition(name);
    }

    @Override
    public Transition findByTransition(String name) {
        return dao.findByTransition(name);
    }

    @Override
    public List<Transition> getAll() {
        return (List<Transition>) dao.findAll();
    }

    @Override
    public List<Transition> findAllByTransition(String name) {
        return dao.findAllByTransition(name);
    }


}
