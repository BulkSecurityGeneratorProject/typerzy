package com.mituta.web.rest;

import com.mituta.Test2App;
import com.mituta.domain.Teseeest;
import com.mituta.repository.TeseeestRepository;
import com.mituta.service.TeseeestService;

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
 * Test class for the TeseeestResource REST controller.
 *
 * @see TeseeestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test2App.class)
@WebAppConfiguration
@IntegrationTest
public class TeseeestResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private TeseeestRepository teseeestRepository;

    @Inject
    private TeseeestService teseeestService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTeseeestMockMvc;

    private Teseeest teseeest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeseeestResource teseeestResource = new TeseeestResource();
        ReflectionTestUtils.setField(teseeestResource, "teseeestService", teseeestService);
        this.restTeseeestMockMvc = MockMvcBuilders.standaloneSetup(teseeestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        teseeest = new Teseeest();
        teseeest.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTeseeest() throws Exception {
        int databaseSizeBeforeCreate = teseeestRepository.findAll().size();

        // Create the Teseeest

        restTeseeestMockMvc.perform(post("/api/teseeests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teseeest)))
                .andExpect(status().isCreated());

        // Validate the Teseeest in the database
        List<Teseeest> teseeests = teseeestRepository.findAll();
        assertThat(teseeests).hasSize(databaseSizeBeforeCreate + 1);
        Teseeest testTeseeest = teseeests.get(teseeests.size() - 1);
        assertThat(testTeseeest.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllTeseeests() throws Exception {
        // Initialize the database
        teseeestRepository.saveAndFlush(teseeest);

        // Get all the teseeests
        restTeseeestMockMvc.perform(get("/api/teseeests?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(teseeest.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTeseeest() throws Exception {
        // Initialize the database
        teseeestRepository.saveAndFlush(teseeest);

        // Get the teseeest
        restTeseeestMockMvc.perform(get("/api/teseeests/{id}", teseeest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(teseeest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeseeest() throws Exception {
        // Get the teseeest
        restTeseeestMockMvc.perform(get("/api/teseeests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeseeest() throws Exception {
        // Initialize the database
        teseeestService.save(teseeest);

        int databaseSizeBeforeUpdate = teseeestRepository.findAll().size();

        // Update the teseeest
        Teseeest updatedTeseeest = new Teseeest();
        updatedTeseeest.setId(teseeest.getId());
        updatedTeseeest.setName(UPDATED_NAME);

        restTeseeestMockMvc.perform(put("/api/teseeests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTeseeest)))
                .andExpect(status().isOk());

        // Validate the Teseeest in the database
        List<Teseeest> teseeests = teseeestRepository.findAll();
        assertThat(teseeests).hasSize(databaseSizeBeforeUpdate);
        Teseeest testTeseeest = teseeests.get(teseeests.size() - 1);
        assertThat(testTeseeest.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTeseeest() throws Exception {
        // Initialize the database
        teseeestService.save(teseeest);

        int databaseSizeBeforeDelete = teseeestRepository.findAll().size();

        // Get the teseeest
        restTeseeestMockMvc.perform(delete("/api/teseeests/{id}", teseeest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Teseeest> teseeests = teseeestRepository.findAll();
        assertThat(teseeests).hasSize(databaseSizeBeforeDelete - 1);
    }
}
