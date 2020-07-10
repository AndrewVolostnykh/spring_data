package com.bsa.springdata.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByLastNameStartingWithIgnoreCase(String lastName, Pageable pageable);

    @Query("select u " +
            " from User u " +
            " inner join u.office o " +
            " where o.city = :city")
    List<User> findByCity(@Param("city") String city, Sort sort);

    List<User> findByExperienceGreaterThanEqualOrderByExperienceDesc(int experience);

    @Query("select u from User u " +
            "inner join u.office o " +
            "inner join u.team t " +
            "where o.city = :city " +
            "and t.room = :room")
    List<User> findByCityAndRoom(@Param("city") String city, @Param("room") String room, Sort sort);

}
