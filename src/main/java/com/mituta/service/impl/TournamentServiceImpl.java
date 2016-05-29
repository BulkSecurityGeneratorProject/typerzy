package com.mituta.service.impl;

import com.mituta.service.TournamentService;
import com.mituta.domain.Tournament;
import com.mituta.repository.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tournament.
 */
@Service
@Transactional
public class TournamentServiceImpl implements TournamentService{

    private final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);
    
    @Inject
    private TournamentRepository tournamentRepository;
    
    /**
     * Save a tournament.
     * 
     * @param tournament the entity to save
     * @return the persisted entity
     */
    public Tournament save(Tournament tournament) {
        log.debug("Request to save Tournament : {}", tournament);
        Tournament result = tournamentRepository.save(tournament);
        return result;
    }

    /**
     *  Get all the tournaments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tournament> findAll(Pageable pageable) {
        log.debug("Request to get all Tournaments");
        Page<Tournament> result = tournamentRepository.findTournamentsOfCurrentUser(pageable); 
        return result;
    }

    /**
     *  Get one tournament by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tournament findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        Tournament tournament = tournamentRepository.findOneWithEagerRelationships(id);
        return tournament;
    }

    /**
     *  Delete the  tournament by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);
        tournamentRepository.delete(id);
    }
}
