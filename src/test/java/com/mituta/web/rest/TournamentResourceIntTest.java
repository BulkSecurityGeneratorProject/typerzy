package com.mituta.web.rest;

import com.mituta.Test2App;
import com.mituta.domain.Tournament;
import com.mituta.repository.TournamentRepository;
import com.mituta.service.TournamentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TournamentResource REST controller.
 *
 * @see TournamentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test2App.class)
@WebAppConfiguration
@IntegrationTest
public class TournamentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private TournamentRepository tournamentRepository;

    @Inject
    private TournamentService tournamentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTournamentMockMvc;

    private Tournament tournament;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TournamentResource tournamentResource = new TournamentResource();
        ReflectionTestUtils.setField(tournamentResource, "tournamentService", tournamentService);
        this.restTournamentMockMvc = MockMvcBuilders.standaloneSetup(tournamentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tournament = new Tournament();
        tournament.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTournament() throws Exception {
        int databaseSizeBeforeCreate = tournamentRepository.findAll().size();

        // Create the Tournament

        restTournamentMockMvc.perform(post("/api/tournaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournament)))
                .andExpect(status().isCreated());

        // Validate the Tournament in the database
        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeCreate + 1);
        Tournament testTournament = tournaments.get(tournaments.size() - 1);
        assertThat(testTournament.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentRepository.findAll().size();
        // set the field null
        tournament.setName(null);

        // Create the Tournament, which fails.

        restTournamentMockMvc.perform(post("/api/tournaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tournament)))
                .andExpect(status().isBadRequest());

        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTournaments() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournaments
        restTournamentMockMvc.perform(get("/api/tournaments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tournament.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", tournament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tournament.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTournament() throws Exception {
        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournament() throws Exception {
        // Initialize the database
        tournamentService.save(tournament);

        int databaseSizeBeforeUpdate = tournamentRepository.findAll().size();

        // Update the tournament
        Tournament updatedTournament = new Tournament();
        updatedTournament.setId(tournament.getId());
        updatedTournament.setName(UPDATED_NAME);

        restTournamentMockMvc.perform(put("/api/tournaments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTournament)))
                .andExpect(status().isOk());

        // Validate the Tournament in the database
        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeUpdate);
        Tournament testTournament = tournaments.get(tournaments.size() - 1);
        assertThat(testTournament.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTournament() throws Exception {
        // Initialize the database
        tournamentService.save(tournament);

        int databaseSizeBeforeDelete = tournamentRepository.findAll().size();

        // Get the tournament
        restTournamentMockMvc.perform(delete("/api/tournaments/{id}", tournament.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tournament> tournaments = tournamentRepository.findAll();
        assertThat(tournaments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
