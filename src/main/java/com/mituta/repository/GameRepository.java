package com.mituta.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mituta.domain.Game;

/**
 * Spring Data JPA repository for the Game entity.
 */
public interface GameRepository extends JpaRepository<Game,Long> {

	@Query("SELECT g FROM Game g  where g.tournament.id  =:id")
	Page<Game> findByTournamentId(Pageable pageable, @Param("id") Long id);

}
