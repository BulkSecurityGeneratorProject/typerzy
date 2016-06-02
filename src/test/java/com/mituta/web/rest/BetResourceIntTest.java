package com.mituta.web.rest;

import com.mituta.Test2App;
import com.mituta.domain.Bet;
import com.mituta.repository.BetRepository;
import com.mituta.service.BetService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BetResource REST controller.
 *
 * @see BetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test2App.class)
@WebAppConfiguration
@IntegrationTest
public class BetResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.format(DEFAULT_TIME);

    @Inject
    private BetRepository betRepository;

    @Inject
    private BetService betService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBetMockMvc;

    private Bet bet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BetResource betResource = new BetResource();
        ReflectionTestUtils.setField(betResource, "betService", betService);
        this.restBetMockMvc = MockMvcBuilders.standaloneSetup(betResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bet = new Bet();
        bet.setTime(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createBet() throws Exception {
        int databaseSizeBeforeCreate = betRepository.findAll().size();

        // Create the Bet

        restBetMockMvc.perform(post("/api/bets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bet)))
                .andExpect(status().isCreated());

        // Validate the Bet in the database
        List<Bet> bets = betRepository.findAll();
        assertThat(bets).hasSize(databaseSizeBeforeCreate + 1);
        Bet testBet = bets.get(bets.size() - 1);
        assertThat(testBet.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = betRepository.findAll().size();
        // set the field null
        bet.setTime(null);

        // Create the Bet, which fails.

        restBetMockMvc.perform(post("/api/bets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bet)))
                .andExpect(status().isBadRequest());

        List<Bet> bets = betRepository.findAll();
        assertThat(bets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBets() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        // Get all the bets
        restBetMockMvc.perform(get("/api/bets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bet.getId().intValue())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)));
    }

    @Test
    @Transactional
    public void getBet() throws Exception {
        // Initialize the database
        betRepository.saveAndFlush(bet);

        // Get the bet
        restBetMockMvc.perform(get("/api/bets/{id}", bet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bet.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBet() throws Exception {
        // Get the bet
        restBetMockMvc.perform(get("/api/bets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBet() throws Exception {
        // Initialize the database
        betService.save(bet);

        int databaseSizeBeforeUpdate = betRepository.findAll().size();

        // Update the bet
        Bet updatedBet = new Bet();
        updatedBet.setId(bet.getId());
        updatedBet.setTime(UPDATED_TIME);

        restBetMockMvc.perform(put("/api/bets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBet)))
                .andExpect(status().isOk());

        // Validate the Bet in the database
        List<Bet> bets = betRepository.findAll();
        assertThat(bets).hasSize(databaseSizeBeforeUpdate);
        Bet testBet = bets.get(bets.size() - 1);
        assertThat(testBet.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteBet() throws Exception {
        // Initialize the database
        betService.save(bet);

        int databaseSizeBeforeDelete = betRepository.findAll().size();

        // Get the bet
        restBetMockMvc.perform(delete("/api/bets/{id}", bet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bet> bets = betRepository.findAll();
        assertThat(bets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
