package com.mike.moneyman.dao;

import com.mike.moneyman.domain.Pipeline;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PipelineDAO extends CrudRepository<Pipeline,Long> {

    @Modifying
    @Query(value = "update Pipeline" +
            " p set p.name=:name,p.description=:description,p.status=:status," +
            "p.startTime=:startTime,p.endTime=:endTime where p." +
            "id=:id")
    void update(@Param("name")String name,
                @Param("description") String description,
                @Param("status")String status,
                @Param("startTime")String startTime,
                @Param("endTime")String endTime,
                @Param("id")Long id);

    @Query("select p from Pipeline p where p.name=:name")
    Pipeline findByName(@Param("name") String name);
}
