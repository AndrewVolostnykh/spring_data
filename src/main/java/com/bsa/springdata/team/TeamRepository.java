package com.bsa.springdata.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByName(String name);
    int countByTechnologyName(String name);

    //select p.name from Project p where t.project_id = p.id
//    t.name ||  from Project p Team t on p.id = t.projectID

    // "update Team t set t.name = concat(t.name, '_', t.projectName , '_', t.technologyName) where t.name = :hipsters"

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update teams as tt " +
            "set name = tt.name || '_' || p.name || '_' || t.name " +
            "from technologies as t, projects as p " +
            "where tt.project_id = p.id and t.id = tt.technology_id and tt.name = :hipsters")
    void normalizeName(@Param("hipsters") String hipsters);

//    @Query(nativeQuery = true, value = "update teams as t " +
//            "set t.technology_id = (select tech.id from technologies as tech where tech.name = :newTechName) " +
//            "where t.technology_id = (select tech.id from technologies as tech where tech.name = :oldTechName) " +
//            "and (select count(u) from users as u inner join teams as team on u.team_id = team.id " +
//            "group by team.id) < :devsNumber")
//    void updateName(@Param("devsNumber") int devsNumber,
//                    @Param("oldTechName") String oldTechnologyName,
//                    @Param("newTechName") String newTechnologyName);


}
