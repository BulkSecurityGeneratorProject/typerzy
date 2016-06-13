package com.mituta.service;

import com.mituta.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Team.
 */
public interface TeamService {

    /**
     * Save a team.
     * 
     * @param team the entity to save
     * @return the persisted entity
     */
    Team save(Team team);

    /**
     *  Get all the teams.
     *  
     *  @return the list of entities
     */
    List<Team> findAll( );

    /**
     *  Get the "id" team.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Team findOne(Long id);

    /**
     *  Delete the "id" team.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
