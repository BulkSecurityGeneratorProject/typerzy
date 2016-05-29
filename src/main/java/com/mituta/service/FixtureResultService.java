package com.mituta.service;

import com.mituta.domain.FixtureResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing FixtureResult.
 */
public interface FixtureResultService {

    /**
     * Save a fixtureResult.
     * 
     * @param fixtureResult the entity to save
     * @return the persisted entity
     */
    FixtureResult save(FixtureResult fixtureResult);

    /**
     *  Get all the fixtureResults.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FixtureResult> findAll(Pageable pageable);
    /**
     *  Get all the fixtureResults where Game is null.
     *  
     *  @return the list of entities
     */
    List<FixtureResult> findAllWhereGameIsNull();

    /**
     *  Get the "id" fixtureResult.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    FixtureResult findOne(Long id);

    /**
     *  Delete the "id" fixtureResult.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
