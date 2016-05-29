package com.mituta.service.impl;

import com.mituta.service.TeseeestService;
import com.mituta.domain.Teseeest;
import com.mituta.repository.TeseeestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Teseeest.
 */
@Service
@Transactional
public class TeseeestServiceImpl implements TeseeestService{

    private final Logger log = LoggerFactory.getLogger(TeseeestServiceImpl.class);
    
    @Inject
    private TeseeestRepository teseeestRepository;
    
    /**
     * Save a teseeest.
     * 
     * @param teseeest the entity to save
     * @return the persisted entity
     */
    public Teseeest save(Teseeest teseeest) {
        log.debug("Request to save Teseeest : {}", teseeest);
        Teseeest result = teseeestRepository.save(teseeest);
        return result;
    }

    /**
     *  Get all the teseeests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Teseeest> findAll(Pageable pageable) {
        log.debug("Request to get all Teseeests");
        Page<Teseeest> result = teseeestRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one teseeest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Teseeest findOne(Long id) {
        log.debug("Request to get Teseeest : {}", id);
        Teseeest teseeest = teseeestRepository.findOne(id);
        return teseeest;
    }

    /**
     *  Delete the  teseeest by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Teseeest : {}", id);
        teseeestRepository.delete(id);
    }
}
