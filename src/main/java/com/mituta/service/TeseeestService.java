package com.mituta.service;

import com.mituta.domain.Teseeest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Teseeest.
 */
public interface TeseeestService {

    /**
     * Save a teseeest.
     * 
     * @param teseeest the entity to save
     * @return the persisted entity
     */
    Teseeest save(Teseeest teseeest);

    /**
     *  Get all the teseeests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Teseeest> findAll(Pageable pageable);

    /**
     *  Get the "id" teseeest.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Teseeest findOne(Long id);

    /**
     *  Delete the "id" teseeest.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
