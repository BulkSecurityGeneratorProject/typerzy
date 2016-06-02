package com.mituta.service;

import com.mituta.domain.Bet;
import com.mituta.repository.BetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Bet.
 */
@Service
@Transactional
public class BetService {

    private final Logger log = LoggerFactory.getLogger(BetService.class);
    
    @Inject
    private BetRepository betRepository;
    
    /**
     * Save a bet.
     * 
     * @param bet the entity to save
     * @return the persisted entity
     */
    public Bet save(Bet bet) {
        log.debug("Request to save Bet : {}", bet);
        Bet result = betRepository.save(bet);
        return result;
    }

    /**
     *  Get all the bets.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Bet> findAll() {
        log.debug("Request to get all Bets");
        List<Bet> result = betRepository.findAll();
        return result;
    }

    /**
     *  Get one bet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Bet findOne(Long id) {
        log.debug("Request to get Bet : {}", id);
        Bet bet = betRepository.findOne(id);
        return bet;
    }

    /**
     *  Delete the  bet by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bet : {}", id);
        betRepository.delete(id);
    }
}
