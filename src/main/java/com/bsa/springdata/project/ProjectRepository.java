package com.bsa.springdata.project;

import com.bsa.springdata.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query(nativeQuery = true, value = "select projects.* from projects as p " +
            "inner join teams t on p.id = t.project_id " +
            " inner join technologies t2 on t.technology_id = t2.id " +
            "inner join users u on t.id = u.team_id" +
            " where t2.name = :technology " +
            " group by project_id")
    List<Project> findByTechnology(@Param("technology") String technology, Pageable pageable);
}