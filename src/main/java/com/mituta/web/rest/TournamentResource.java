package com.mituta.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mituta.domain.Tournament;
import com.mituta.service.TournamentService;
import com.mituta.web.rest.util.HeaderUtil;
import com.mituta.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Tournament.
 */
@RestController
@RequestMapping("/api")
public class TournamentResource {

    private final Logger log = LoggerFactory.getLogger(TournamentResource.class);
        
    @Inject
    private TournamentService tournamentService;
    
    /**
     * POST  /tournaments : Create a new tournament.
     *
     * @param tournament the tournament to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tournament, or with status 400 (Bad Request) if the tournament has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tournaments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tournament> createTournament(@Valid @RequestBody Tournament tournament) throws URISyntaxException {
        log.debug("REST request to save Tournament : {}", tournament);
        if (tournament.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tournament", "idexists", "A new tournament cannot already have an ID")).body(null);
        }
        Tournament result = tournamentService.save(tournament);
        return ResponseEntity.created(new URI("/api/tournaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tournament", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tournaments : Updates an existing tournament.
     *
     * @param tournament the tournament to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tournament,
     * or with status 400 (Bad Request) if the tournament is not valid,
     * or with status 500 (Internal Server Error) if the tournament couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tournaments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tournament> updateTournament(@Valid @RequestBody Tournament tournament) throws URISyntaxException {
        log.debug("REST request to update Tournament : {}", tournament);
        if (tournament.getId() == null) {
            return createTournament(tournament);
        }
        Tournament result = tournamentService.save(tournament);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tournament", tournament.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tournaments : get all the tournaments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tournaments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tournaments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tournament>> getAllTournaments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tournaments");
        Page<Tournament> page = tournamentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tournaments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tournaments/:id : get the "id" tournament.
     *
     * @param id the id of the tournament to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tournament, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tournaments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tournament> getTournament(@PathVariable Long id) {
        log.debug("REST request to get Tournament : {}", id);
        Tournament tournament = tournamentService.findOne(id);
        return Optional.ofNullable(tournament)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tournaments/:id : delete the "id" tournament.
     *
     * @param id the id of the tournament to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tournaments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        log.debug("REST request to delete Tournament : {}", id);
        tournamentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tournament", id.toString())).build();
    }

}
