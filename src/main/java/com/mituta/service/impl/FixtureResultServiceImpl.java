package com.mituta.service.impl;

import com.mituta.service.FixtureResultService;
import com.mituta.domain.FixtureResult;
import com.mituta.repository.FixtureResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing FixtureResult.
 */
@Service
@Transactional
public class FixtureResultServiceImpl implements FixtureResultService{

    private final Logger log = LoggerFactory.getLogger(FixtureResultServiceImpl.class);
    
    @Inject
    private FixtureResultRepository fixtureResultRepository;
    
    /**
     * Save a fixtureResult.
     * 
     * @param fixtureResult the entity to save
     * @return the persisted entity
     */
    public FixtureResult save(FixtureResult fixtureResult) {
        log.debug("Request to save FixtureResult : {}", fixtureResult);
        FixtureResult result = fixtureResultRepository.save(fixtureResult);
        return result;
    }

    /**
     *  Get all the fixtureResults.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<FixtureResult> findAll(Pageable pageable) {
        log.debug("Request to get all FixtureResults");
        Page<FixtureResult> result = fixtureResultRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the fixtureResults where Game is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<FixtureResult> findAllWhereGameIsNull() {
        log.debug("Request to get all fixtureResults where Game is null");
        return StreamSupport
            .stream(fixtureResultRepository.findAll().spliterator(), false)
            .filter(fixtureResult -> fixtureResult.getGame() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one fixtureResult by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FixtureResult findOne(Long id) {
        log.debug("Request to get FixtureResult : {}", id);
        FixtureResult fixtureResult = fixtureResultRepository.findOne(id);
        return fixtureResult;
    }

    /**
     *  Delete the  fixtureResult by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FixtureResult : {}", id);
        fixtureResultRepository.delete(id);
    }
}
