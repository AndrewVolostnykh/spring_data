package com.bsa.springdata.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public interface OfficeRepository extends JpaRepository<Office, UUID> {

    @Query("select o from Office o " +
            "inner join o.users u " +
            "inner join u.team t " +
            "inner join t.project p " +
            "inner join t.technology tchg " +
            "where tchg.name = :technology" +
            " group by o.id")
    List<Office> getByTechnology(@Param("technology") String technology);

    @Modifying
    @Transactional
    @Query(value = "update offices as off " +
            "set address = :address " +
            "from ( " +
            "         select o.id from offices as o " +
            "             inner join users u on o.id = u.office_id " +
            "             inner join teams t on u.team_id = t.id " +
            "             inner join projects p on t.project_id = p.id " +
            "             where o.address = :old_address " +
            "         ) as o " +
            "where o.id = off.id", nativeQuery = true)
    void updateOfficeAddress(@Param("old_address") String oldAddress, @Param("address") String newAddress);

    Office findByAddress(String address);
}
