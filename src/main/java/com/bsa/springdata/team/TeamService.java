package com.bsa.springdata.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TechnologyRepository technologyRepository;

    public void updateTechnology(int devsNumber, String oldTechnologyName, String newTechnologyName) {
        // TODO: You can use several queries here. Try to keep it as simple as possible

//        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("org.hibernate.jpa.HibernatePersistenceProvider");
//        EntityManager entityManager = emFactory.createEntityManager();
//        Query query = entityManager.createNativeQuery(
//                "update Team t " +
//                        "set t.technology_id = (select tech.id from Technology tech where tech.name = :newTechName) " +
//                        "where t.technology_id = (select tech.id from Technology tech where tech.name = :oldTechName) " +
//                        "and (select count(u) from User u inner join u.team team group by team.id) < :devsNumber"
//        );
//
//        query.setParameter(1, newTechnologyName)
//                .setParameter(2, oldTechnologyName)
//                .setParameter(3, devsNumber)
//                .executeUpdate();

        //teamRepository.updateName(devsNumber, oldTechnologyName, newTechnologyName);
    }

    public void normalizeName(String hipsters) {
        // TODO: Use a single query. You need to create a native query
        teamRepository.normalizeName(hipsters); // ???
    }
}
