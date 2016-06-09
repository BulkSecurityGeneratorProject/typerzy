package com.mituta.repository;

import java.util.List;

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

	@Query("SELECT g FROM Game g  where g.tournament.id  =:id order by g.time asc")
	List<Game> findByTournamentId(@Param("id") Long id);

}
