package com.mituta.repository;

import com.mituta.domain.Game;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Game entity.
 */
public interface GameRepository extends JpaRepository<Game,Long> {

}
