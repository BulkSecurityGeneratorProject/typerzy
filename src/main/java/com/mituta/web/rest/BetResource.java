package com.mituta.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mituta.domain.Bet;
import com.mituta.domain.Game;
import com.mituta.service.BetService;
import com.mituta.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bet.
 */
@RestController
@RequestMapping("/api")
public class BetResource {

    private final Logger log = LoggerFactory.getLogger(BetResource.class);
        
    @Inject
    private BetService betService;
    
    /**
     * POST  /bets : Create a new bet.
     *
     * @param bet the bet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bet, or with status 400 (Bad Request) if the bet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/bets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bet> createBet( @RequestBody Bet bet) throws URISyntaxException {
        log.debug("REST request to save Bet : {}", bet);
        if (bet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bet", "idexists", "A new bet cannot already have an ID")).body(null);
        }
        Bet result = betService.save(bet);
        return ResponseEntity.created(new URI("/api/bets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bets : Updates an existing bet.
     *
     * @param bet the bet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bet,
     * or with status 400 (Bad Request) if the bet is not valid,
     * or with status 500 (Internal Server Error) if the bet couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/bets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bet> updateBet( @RequestBody Bet bet) throws URISyntaxException {
        log.debug("REST request to update Bet : {}", bet);
        if (bet.getId() == null) {
            return createBet(bet);
        }
        Bet result = betService.save(bet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bet", bet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bets : get all the bets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bets in body
     */
    @RequestMapping(value = "/bets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Bet> getAllBets() {
        log.debug("REST request to get all Bets");
        return betService.findAll();
    }

    /**
     * PUT  /bets : get all the bets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bets in body
     */
    @RequestMapping(value = "/bets/game",
    		method = RequestMethod.PUT,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Bet> getBetsForGame(@RequestBody Game game) {
    	log.debug("REST request to get all Bets");
    	return betService.getForGame(game);
    }

    @RequestMapping(value = "/bets/game/{id}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Bet getUserBetForGame(@PathVariable Long id) {
    	log.debug("REST request to get all Bets");
    	return betService.getForCurrentUserAndGame(id);
    }

    /**
     * GET  /bets/:id : get the "id" bet.
     *
     * @param id the id of the bet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bet, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/bets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bet> getBet(@PathVariable Long id) {
        log.debug("REST request to get Bet : {}", id);
        Bet bet = betService.findOne(id);
        return Optional.ofNullable(bet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bets/:id : delete the "id" bet.
     *
     * @param id the id of the bet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/bets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBet(@PathVariable Long id) {
        log.debug("REST request to delete Bet : {}", id);
        betService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bet", id.toString())).build();
    }

}
