package com.mituta.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mituta.domain.Teseeest;
import com.mituta.service.TeseeestService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Teseeest.
 */
@RestController
@RequestMapping("/api")
public class TeseeestResource {

    private final Logger log = LoggerFactory.getLogger(TeseeestResource.class);
        
    @Inject
    private TeseeestService teseeestService;
    
    /**
     * POST  /teseeests : Create a new teseeest.
     *
     * @param teseeest the teseeest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teseeest, or with status 400 (Bad Request) if the teseeest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/teseeests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teseeest> createTeseeest(@RequestBody Teseeest teseeest) throws URISyntaxException {
        log.debug("REST request to save Teseeest : {}", teseeest);
        if (teseeest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("teseeest", "idexists", "A new teseeest cannot already have an ID")).body(null);
        }
        Teseeest result = teseeestService.save(teseeest);
        return ResponseEntity.created(new URI("/api/teseeests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("teseeest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teseeests : Updates an existing teseeest.
     *
     * @param teseeest the teseeest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teseeest,
     * or with status 400 (Bad Request) if the teseeest is not valid,
     * or with status 500 (Internal Server Error) if the teseeest couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/teseeests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teseeest> updateTeseeest(@RequestBody Teseeest teseeest) throws URISyntaxException {
        log.debug("REST request to update Teseeest : {}", teseeest);
        if (teseeest.getId() == null) {
            return createTeseeest(teseeest);
        }
        Teseeest result = teseeestService.save(teseeest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("teseeest", teseeest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teseeests : get all the teseeests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teseeests in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/teseeests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Teseeest>> getAllTeseeests(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Teseeests");
        Page<Teseeest> page = teseeestService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teseeests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /teseeests/:id : get the "id" teseeest.
     *
     * @param id the id of the teseeest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teseeest, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/teseeests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teseeest> getTeseeest(@PathVariable Long id) {
        log.debug("REST request to get Teseeest : {}", id);
        Teseeest teseeest = teseeestService.findOne(id);
        return Optional.ofNullable(teseeest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /teseeests/:id : delete the "id" teseeest.
     *
     * @param id the id of the teseeest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/teseeests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTeseeest(@PathVariable Long id) {
        log.debug("REST request to delete Teseeest : {}", id);
        teseeestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("teseeest", id.toString())).build();
    }

}
