package com.mituta.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mituta.domain.FixtureResult;
import com.mituta.service.FixtureResultService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing FixtureResult.
 */
@RestController
@RequestMapping("/api")
public class FixtureResultResource {

    private final Logger log = LoggerFactory.getLogger(FixtureResultResource.class);
        
    @Inject
    private FixtureResultService fixtureResultService;
    
    /**
     * POST  /fixture-results : Create a new fixtureResult.
     *
     * @param fixtureResult the fixtureResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fixtureResult, or with status 400 (Bad Request) if the fixtureResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/fixture-results",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureResult> createFixtureResult(@Valid @RequestBody FixtureResult fixtureResult) throws URISyntaxException {
        log.debug("REST request to save FixtureResult : {}", fixtureResult);
        if (fixtureResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fixtureResult", "idexists", "A new fixtureResult cannot already have an ID")).body(null);
        }
        FixtureResult result = fixtureResultService.save(fixtureResult);
        return ResponseEntity.created(new URI("/api/fixture-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fixtureResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fixture-results : Updates an existing fixtureResult.
     *
     * @param fixtureResult the fixtureResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fixtureResult,
     * or with status 400 (Bad Request) if the fixtureResult is not valid,
     * or with status 500 (Internal Server Error) if the fixtureResult couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/fixture-results",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureResult> updateFixtureResult(@Valid @RequestBody FixtureResult fixtureResult) throws URISyntaxException {
        log.debug("REST request to update FixtureResult : {}", fixtureResult);
        if (fixtureResult.getId() == null) {
            return createFixtureResult(fixtureResult);
        }
        FixtureResult result = fixtureResultService.save(fixtureResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fixtureResult", fixtureResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fixture-results : get all the fixtureResults.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of fixtureResults in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/fixture-results",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FixtureResult>> getAllFixtureResults(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("game-is-null".equals(filter)) {
            log.debug("REST request to get all FixtureResults where game is null");
            return new ResponseEntity<>(fixtureResultService.findAllWhereGameIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of FixtureResults");
        Page<FixtureResult> page = fixtureResultService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fixture-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fixture-results/:id : get the "id" fixtureResult.
     *
     * @param id the id of the fixtureResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fixtureResult, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/fixture-results/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FixtureResult> getFixtureResult(@PathVariable Long id) {
        log.debug("REST request to get FixtureResult : {}", id);
        FixtureResult fixtureResult = fixtureResultService.findOne(id);
        return Optional.ofNullable(fixtureResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fixture-results/:id : delete the "id" fixtureResult.
     *
     * @param id the id of the fixtureResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/fixture-results/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFixtureResult(@PathVariable Long id) {
        log.debug("REST request to delete FixtureResult : {}", id);
        fixtureResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fixtureResult", id.toString())).build();
    }

}
