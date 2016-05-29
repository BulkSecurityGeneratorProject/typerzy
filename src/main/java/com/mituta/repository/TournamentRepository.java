package com.mituta.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mituta.domain.Tournament;

/**
 * Spring Data JPA repository for the Tournament entity.
 */
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

    @Query("select distinct tournament from Tournament tournament left join fetch tournament.players")
    List<Tournament> findAllWithEagerRelationships();

    @Query("select tournament from Tournament tournament left join fetch tournament.players left join fetch tournament.fixtures  where tournament.id =:id")
    Tournament findOneWithEagerRelationships(@Param("id") Long id);


    @Query("SELECT t FROM Tournament t  INNER JOIN t.players p WHERE p.login = ?#{principal.username}")
    Page<Tournament> findTournamentsOfCurrentUser(Pageable pageable);
}
