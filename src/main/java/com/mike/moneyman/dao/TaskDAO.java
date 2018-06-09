package com.mike.moneyman.dao;

import com.mike.moneyman.domain.Pipeline;
import com.mike.moneyman.domain.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TaskDAO extends CrudRepository<Task, Long> {

    @Modifying
    @Query("update Task t set t.status=:status,t.startTime=:startTime,t.endTime=:endTime where t.id=:id")
    void update(@Param("status") String status,
                @Param("startTime") String startTime,
                @Param("endTime") String endTime,
                @Param("id")Long id);

    @Query("select t from Task t where t.name=:name")
    Task findByName(@Param("name") String name);
}
