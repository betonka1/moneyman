package com.mike.moneyman.service;

import com.mike.moneyman.domain.Transition;

import java.util.List;

public interface TransitionService {

    void save(Iterable<Transition> transition);

    Integer count(String name);

    Transition findByTransition(String name);

    List<Transition> getAll();

    List<Transition> findAllByTransition(String name);

}
