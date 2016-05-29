package com.mituta.web.rest;

import com.mituta.Test2App;
import com.mituta.domain.FixtureResult;
import com.mituta.repository.FixtureResultRepository;
import com.mituta.service.FixtureResultService;

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
 * Test class for the FixtureResultResource REST controller.
 *
 * @see FixtureResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test2App.class)
@WebAppConfiguration
@IntegrationTest
public class FixtureResultResourceIntTest {


    private static final Integer DEFAULT_HOME = 1;
    private static final Integer UPDATED_HOME = 2;

    private static final Integer DEFAULT_AWAY = 0;
    private static final Integer UPDATED_AWAY = 1;

    @Inject
    private FixtureResultRepository fixtureResultRepository;

    @Inject
    private FixtureResultService fixtureResultService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFixtureResultMockMvc;

    private FixtureResult fixtureResult;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FixtureResultResource fixtureResultResource = new FixtureResultResource();
        ReflectionTestUtils.setField(fixtureResultResource, "fixtureResultService", fixtureResultService);
        this.restFixtureResultMockMvc = MockMvcBuilders.standaloneSetup(fixtureResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fixtureResult = new FixtureResult();
        fixtureResult.setHome(DEFAULT_HOME);
        fixtureResult.setAway(DEFAULT_AWAY);
    }

    @Test
    @Transactional
    public void createFixtureResult() throws Exception {
        int databaseSizeBeforeCreate = fixtureResultRepository.findAll().size();

        // Create the FixtureResult

        restFixtureResultMockMvc.perform(post("/api/fixture-results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fixtureResult)))
                .andExpect(status().isCreated());

        // Validate the FixtureResult in the database
        List<FixtureResult> fixtureResults = fixtureResultRepository.findAll();
        assertThat(fixtureResults).hasSize(databaseSizeBeforeCreate + 1);
        FixtureResult testFixtureResult = fixtureResults.get(fixtureResults.size() - 1);
        assertThat(testFixtureResult.getHome()).isEqualTo(DEFAULT_HOME);
        assertThat(testFixtureResult.getAway()).isEqualTo(DEFAULT_AWAY);
    }

    @Test
    @Transactional
    public void getAllFixtureResults() throws Exception {
        // Initialize the database
        fixtureResultRepository.saveAndFlush(fixtureResult);

        // Get all the fixtureResults
        restFixtureResultMockMvc.perform(get("/api/fixture-results?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fixtureResult.getId().intValue())))
                .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
                .andExpect(jsonPath("$.[*].away").value(hasItem(DEFAULT_AWAY)));
    }

    @Test
    @Transactional
    public void getFixtureResult() throws Exception {
        // Initialize the database
        fixtureResultRepository.saveAndFlush(fixtureResult);

        // Get the fixtureResult
        restFixtureResultMockMvc.perform(get("/api/fixture-results/{id}", fixtureResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fixtureResult.getId().intValue()))
            .andExpect(jsonPath("$.home").value(DEFAULT_HOME))
            .andExpect(jsonPath("$.away").value(DEFAULT_AWAY));
    }

    @Test
    @Transactional
    public void getNonExistingFixtureResult() throws Exception {
        // Get the fixtureResult
        restFixtureResultMockMvc.perform(get("/api/fixture-results/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixtureResult() throws Exception {
        // Initialize the database
        fixtureResultService.save(fixtureResult);

        int databaseSizeBeforeUpdate = fixtureResultRepository.findAll().size();

        // Update the fixtureResult
        FixtureResult updatedFixtureResult = new FixtureResult();
        updatedFixtureResult.setId(fixtureResult.getId());
        updatedFixtureResult.setHome(UPDATED_HOME);
        updatedFixtureResult.setAway(UPDATED_AWAY);

        restFixtureResultMockMvc.perform(put("/api/fixture-results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFixtureResult)))
                .andExpect(status().isOk());

        // Validate the FixtureResult in the database
        List<FixtureResult> fixtureResults = fixtureResultRepository.findAll();
        assertThat(fixtureResults).hasSize(databaseSizeBeforeUpdate);
        FixtureResult testFixtureResult = fixtureResults.get(fixtureResults.size() - 1);
        assertThat(testFixtureResult.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testFixtureResult.getAway()).isEqualTo(UPDATED_AWAY);
    }

    @Test
    @Transactional
    public void deleteFixtureResult() throws Exception {
        // Initialize the database
        fixtureResultService.save(fixtureResult);

        int databaseSizeBeforeDelete = fixtureResultRepository.findAll().size();

        // Get the fixtureResult
        restFixtureResultMockMvc.perform(delete("/api/fixture-results/{id}", fixtureResult.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FixtureResult> fixtureResults = fixtureResultRepository.findAll();
        assertThat(fixtureResults).hasSize(databaseSizeBeforeDelete - 1);
    }
}
