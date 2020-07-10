package com.bsa.springdata.project;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query(nativeQuery = true, value = "select p.*, count(u.id) as countUser from projects p\n" +
            "inner join teams t on t.project_id =  p.id\n" +
            "inner join technologies tl on t.technology_id = tl.id\n" +
            "inner join users u on u.team_id = t.id " +
            "where tl.name = :technology " +
            "group by p.id " +
            "order by countUser DESC")
    List<Project> findByTechnology(@Param("technology") String technology, Pageable pageable);

    @Query(value = "select p.*, count(t.id) as countTeam, count(u.id) as countUser " +
            "from projects p " +
            "         inner join teams t on p.id = t.project_id " +
            "         inner join users u on t.id = u.team_id " +
            "group by p.id " +
            "order by countTeam DESC, countUser DESC, p.name DESC limit 1", nativeQuery = true)
    List<Project> findTheBiggest();

    @Query(value = "select count(t.proj_id) from (select proj.id as proj_id from projects proj \n" +
            "    inner join teams t on proj.id = t.project_id\n" +
            "    inner join users u on t.id = u.team_id\n" +
            "    inner join user2role u2r on u.id = u2r.user_id\n" +
            "    inner join roles r on u2r.role_id = r.id\n" +
            "    where r.name = :role\n" +
            "    group by proj.id\n" +
            "    ) t", nativeQuery = true)
    int countByUsersRoles(@Param("role") String role);

}