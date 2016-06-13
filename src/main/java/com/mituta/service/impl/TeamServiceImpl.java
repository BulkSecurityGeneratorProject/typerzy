package com.mituta.service.impl;

import com.mituta.service.TeamService;
import com.mituta.domain.Team;
import com.mituta.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService{

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);
    
    @Inject
    private TeamRepository teamRepository;
    
    /**
     * Save a team.
     * 
     * @param team the entity to save
     * @return the persisted entity
     */
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        Team result = teamRepository.save(team);
        return result;
    }

    /**
     *  Get all the teams.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Team> findAll( ) {
        log.debug("Request to get all Teams");
        List<Team> result = teamRepository.findAll(); 
        return result;
    }

    /**
     *  Get one team by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Team findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOne(id);
        return team;
    }

    /**
     *  Delete the  team by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.delete(id);
    }
}
