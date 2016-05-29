package com.mituta.service;

import com.mituta.domain.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tournament.
 */
public interface TournamentService {

    /**
     * Save a tournament.
     * 
     * @param tournament the entity to save
     * @return the persisted entity
     */
    Tournament save(Tournament tournament);

    /**
     *  Get all the tournaments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tournament> findAll(Pageable pageable);

    /**
     *  Get the "id" tournament.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tournament findOne(Long id);

    /**
     *  Delete the "id" tournament.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
