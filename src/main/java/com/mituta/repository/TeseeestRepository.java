package com.mituta.repository;

import com.mituta.domain.Teseeest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Teseeest entity.
 */
public interface TeseeestRepository extends JpaRepository<Teseeest,Long> {

}
