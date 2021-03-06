package com.mituta.service;

import com.mituta.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Game.
 */
public interface GameService {

    /**
     * Save a game.
     * 
     * @param game the entity to save
     * @return the persisted entity
     */
    Game save(Game game);

    /**
     *  Get all the games.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Game> findAll(Pageable pageable);

    /**
     *  Get the "id" game.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Game findOne(Long id);

    /**
     *  Delete the "id" game.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    List<Game> findForTournament(Long aTournament);
}
