package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_pendidikan;
import id.co.askrindo.aos.repository.R_pendidikanRepository;
import id.co.askrindo.aos.repository.search.R_pendidikanSearchRepository;

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
 * Test class for the R_pendidikanResource REST controller.
 *
 * @see R_pendidikanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_pendidikanResourceIntTest {


    private static final Integer DEFAULT_ID_R_PENDIDIKAN = 1;
    private static final Integer UPDATED_ID_R_PENDIDIKAN = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_pendidikanRepository r_pendidikanRepository;

    @Inject
    private R_pendidikanSearchRepository r_pendidikanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_pendidikanMockMvc;

    private R_pendidikan r_pendidikan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_pendidikanResource r_pendidikanResource = new R_pendidikanResource();
        ReflectionTestUtils.setField(r_pendidikanResource, "r_pendidikanSearchRepository", r_pendidikanSearchRepository);
        ReflectionTestUtils.setField(r_pendidikanResource, "r_pendidikanRepository", r_pendidikanRepository);
        this.restR_pendidikanMockMvc = MockMvcBuilders.standaloneSetup(r_pendidikanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_pendidikan = new R_pendidikan();
        r_pendidikan.setId_r_pendidikan(DEFAULT_ID_R_PENDIDIKAN);
        r_pendidikan.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_pendidikan() throws Exception {
        int databaseSizeBeforeCreate = r_pendidikanRepository.findAll().size();

        // Create the R_pendidikan

        restR_pendidikanMockMvc.perform(post("/api/r_pendidikans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_pendidikan)))
                .andExpect(status().isCreated());

        // Validate the R_pendidikan in the database
        List<R_pendidikan> r_pendidikans = r_pendidikanRepository.findAll();
        assertThat(r_pendidikans).hasSize(databaseSizeBeforeCreate + 1);
        R_pendidikan testR_pendidikan = r_pendidikans.get(r_pendidikans.size() - 1);
        assertThat(testR_pendidikan.getId_r_pendidikan()).isEqualTo(DEFAULT_ID_R_PENDIDIKAN);
        assertThat(testR_pendidikan.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_pendidikans() throws Exception {
        // Initialize the database
        r_pendidikanRepository.saveAndFlush(r_pendidikan);

        // Get all the r_pendidikans
        restR_pendidikanMockMvc.perform(get("/api/r_pendidikans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_pendidikan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_pendidikan").value(hasItem(DEFAULT_ID_R_PENDIDIKAN)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_pendidikan() throws Exception {
        // Initialize the database
        r_pendidikanRepository.saveAndFlush(r_pendidikan);

        // Get the r_pendidikan
        restR_pendidikanMockMvc.perform(get("/api/r_pendidikans/{id}", r_pendidikan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_pendidikan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_pendidikan").value(DEFAULT_ID_R_PENDIDIKAN))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_pendidikan() throws Exception {
        // Get the r_pendidikan
        restR_pendidikanMockMvc.perform(get("/api/r_pendidikans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_pendidikan() throws Exception {
        // Initialize the database
        r_pendidikanRepository.saveAndFlush(r_pendidikan);

		int databaseSizeBeforeUpdate = r_pendidikanRepository.findAll().size();

        // Update the r_pendidikan
        r_pendidikan.setId_r_pendidikan(UPDATED_ID_R_PENDIDIKAN);
        r_pendidikan.setUraian(UPDATED_URAIAN);

        restR_pendidikanMockMvc.perform(put("/api/r_pendidikans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_pendidikan)))
                .andExpect(status().isOk());

        // Validate the R_pendidikan in the database
        List<R_pendidikan> r_pendidikans = r_pendidikanRepository.findAll();
        assertThat(r_pendidikans).hasSize(databaseSizeBeforeUpdate);
        R_pendidikan testR_pendidikan = r_pendidikans.get(r_pendidikans.size() - 1);
        assertThat(testR_pendidikan.getId_r_pendidikan()).isEqualTo(UPDATED_ID_R_PENDIDIKAN);
        assertThat(testR_pendidikan.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_pendidikan() throws Exception {
        // Initialize the database
        r_pendidikanRepository.saveAndFlush(r_pendidikan);

		int databaseSizeBeforeDelete = r_pendidikanRepository.findAll().size();

        // Get the r_pendidikan
        restR_pendidikanMockMvc.perform(delete("/api/r_pendidikans/{id}", r_pendidikan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_pendidikan> r_pendidikans = r_pendidikanRepository.findAll();
        assertThat(r_pendidikans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
