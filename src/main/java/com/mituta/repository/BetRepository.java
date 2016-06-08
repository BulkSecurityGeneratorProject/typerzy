package com.mituta.repository;

import com.mituta.domain.Bet;
import com.mituta.domain.Game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Bet entity.
 */
@SuppressWarnings("unused")
public interface BetRepository extends JpaRepository<Bet,Long> {

	@Query("SELECT b FROM Bet b  where b.fixture.id  =:id")
	List<Bet> findByGameId( @Param("id") Long id);

	@Query("SELECT b FROM Bet b  where b.fixture.id  =:id and b.user.login = ?#{principal.username} ")
	List<Bet> getforCurrentUserAndGame(@Param("id") Long id);
}
