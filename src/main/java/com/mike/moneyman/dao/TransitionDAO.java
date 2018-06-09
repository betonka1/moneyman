package com.mike.moneyman.dao;

import com.mike.moneyman.domain.Pipeline;
import com.mike.moneyman.domain.Task;
import com.mike.moneyman.domain.Transition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransitionDAO extends CrudRepository<Transition,Long> {

    Integer countByTransition(String name);
    Transition findByTransition(String name);
    List<Transition> findAllByTransition(String name);
}
