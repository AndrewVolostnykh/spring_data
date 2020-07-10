package com.bsa.springdata.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByName(String name);

    int countByTechnologyName(String name);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update teams as tt " +
            "set name = tt.name || '_' || p.name || '_' || t.name " +
            "from technologies as t, projects as p " +
            "where tt.project_id = p.id and t.id = tt.technology_id and tt.name = :hipsters")
    void normalizeName(@Param("hipsters") String hipsters);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update teams " +
            "set technology_id = (select id from technologies where name = :new_tech limit 1) " +
            "from ( " +
            "         select t.id as team_id " +
            "         from technologies tl " +
            "                  inner join teams t on tl.id = t.technology_id " +
            "                  inner join users u on t.id = u.team_id " +
            "         where tl.name = :technology " +
            "         group by t.id " +
            "         having count(u.id) < :devsNumber " +
            "     ) as tl " +
            "where tl.team_id = teams.id;")
    void updateName(@Param("devsNumber") int devsNumber,
                    @Param("technology") String oldTechnologyName,
                    @Param("new_tech") String newTechnologyName);


}
