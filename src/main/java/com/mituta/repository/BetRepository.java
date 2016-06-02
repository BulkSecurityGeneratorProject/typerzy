package com.mituta.repository;

import com.mituta.domain.Bet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bet entity.
 */
@SuppressWarnings("unused")
public interface BetRepository extends JpaRepository<Bet,Long> {

}
