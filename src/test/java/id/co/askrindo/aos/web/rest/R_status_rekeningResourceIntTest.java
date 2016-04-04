package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_status_rekening;
import id.co.askrindo.aos.repository.R_status_rekeningRepository;
import id.co.askrindo.aos.repository.search.R_status_rekeningSearchRepository;

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
 * Test class for the R_status_rekeningResource REST controller.
 *
 * @see R_status_rekeningResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_status_rekeningResourceIntTest {


    private static final Integer DEFAULT_ID_R_STATUS_REKENING = 1;
    private static final Integer UPDATED_ID_R_STATUS_REKENING = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_status_rekeningRepository r_status_rekeningRepository;

    @Inject
    private R_status_rekeningSearchRepository r_status_rekeningSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_status_rekeningMockMvc;

    private R_status_rekening r_status_rekening;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_status_rekeningResource r_status_rekeningResource = new R_status_rekeningResource();
        ReflectionTestUtils.setField(r_status_rekeningResource, "r_status_rekeningSearchRepository", r_status_rekeningSearchRepository);
        ReflectionTestUtils.setField(r_status_rekeningResource, "r_status_rekeningRepository", r_status_rekeningRepository);
        this.restR_status_rekeningMockMvc = MockMvcBuilders.standaloneSetup(r_status_rekeningResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_status_rekening = new R_status_rekening();
        r_status_rekening.setId_r_status_rekening(DEFAULT_ID_R_STATUS_REKENING);
        r_status_rekening.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_status_rekening() throws Exception {
        int databaseSizeBeforeCreate = r_status_rekeningRepository.findAll().size();

        // Create the R_status_rekening

        restR_status_rekeningMockMvc.perform(post("/api/r_status_rekenings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_status_rekening)))
                .andExpect(status().isCreated());

        // Validate the R_status_rekening in the database
        List<R_status_rekening> r_status_rekenings = r_status_rekeningRepository.findAll();
        assertThat(r_status_rekenings).hasSize(databaseSizeBeforeCreate + 1);
        R_status_rekening testR_status_rekening = r_status_rekenings.get(r_status_rekenings.size() - 1);
        assertThat(testR_status_rekening.getId_r_status_rekening()).isEqualTo(DEFAULT_ID_R_STATUS_REKENING);
        assertThat(testR_status_rekening.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_status_rekenings() throws Exception {
        // Initialize the database
        r_status_rekeningRepository.saveAndFlush(r_status_rekening);

        // Get all the r_status_rekenings
        restR_status_rekeningMockMvc.perform(get("/api/r_status_rekenings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_status_rekening.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_status_rekening").value(hasItem(DEFAULT_ID_R_STATUS_REKENING)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_status_rekening() throws Exception {
        // Initialize the database
        r_status_rekeningRepository.saveAndFlush(r_status_rekening);

        // Get the r_status_rekening
        restR_status_rekeningMockMvc.perform(get("/api/r_status_rekenings/{id}", r_status_rekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_status_rekening.getId().intValue()))
            .andExpect(jsonPath("$.id_r_status_rekening").value(DEFAULT_ID_R_STATUS_REKENING))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_status_rekening() throws Exception {
        // Get the r_status_rekening
        restR_status_rekeningMockMvc.perform(get("/api/r_status_rekenings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_status_rekening() throws Exception {
        // Initialize the database
        r_status_rekeningRepository.saveAndFlush(r_status_rekening);

		int databaseSizeBeforeUpdate = r_status_rekeningRepository.findAll().size();

        // Update the r_status_rekening
        r_status_rekening.setId_r_status_rekening(UPDATED_ID_R_STATUS_REKENING);
        r_status_rekening.setUraian(UPDATED_URAIAN);

        restR_status_rekeningMockMvc.perform(put("/api/r_status_rekenings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_status_rekening)))
                .andExpect(status().isOk());

        // Validate the R_status_rekening in the database
        List<R_status_rekening> r_status_rekenings = r_status_rekeningRepository.findAll();
        assertThat(r_status_rekenings).hasSize(databaseSizeBeforeUpdate);
        R_status_rekening testR_status_rekening = r_status_rekenings.get(r_status_rekenings.size() - 1);
        assertThat(testR_status_rekening.getId_r_status_rekening()).isEqualTo(UPDATED_ID_R_STATUS_REKENING);
        assertThat(testR_status_rekening.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_status_rekening() throws Exception {
        // Initialize the database
        r_status_rekeningRepository.saveAndFlush(r_status_rekening);

		int databaseSizeBeforeDelete = r_status_rekeningRepository.findAll().size();

        // Get the r_status_rekening
        restR_status_rekeningMockMvc.perform(delete("/api/r_status_rekenings/{id}", r_status_rekening.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_status_rekening> r_status_rekenings = r_status_rekeningRepository.findAll();
        assertThat(r_status_rekenings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
