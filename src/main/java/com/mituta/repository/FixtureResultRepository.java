package com.mituta.repository;

import com.mituta.domain.FixtureResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FixtureResult entity.
 */
public interface FixtureResultRepository extends JpaRepository<FixtureResult,Long> {

}
