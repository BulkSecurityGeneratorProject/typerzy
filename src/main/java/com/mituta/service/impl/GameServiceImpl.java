package com.mituta.service.impl;

import com.mituta.service.GameService;
import com.mituta.domain.Game;
import com.mituta.repository.GameRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Game.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService{

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);
    
    @Inject
    private GameRepository gameRepository;
    
    /**
     * Save a game.
     * 
     * @param game the entity to save
     * @return the persisted entity
     */
    public Game save(Game game) {
        log.debug("Request to save Game : {}", game);
        game.setTime(ZonedDateTime.now());
        Game result = gameRepository.save(game);
        return result;
    }

    /**
     *  Get all the games.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Game> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        Page<Game> result = gameRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one game by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Game findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        Game game = gameRepository.findOne(id);
        return game;
    }

    /**
     *  Delete the  game by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.delete(id);
    }

	@Override
	public Page<Game> findForTournament(Pageable pageable, Long tournament) {
		return gameRepository.findByTournamentId(pageable, tournament);
	}
}
