package com.bsa.springdata.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, UUID> {
}
