package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_pekerjaan;
import id.co.askrindo.aos.repository.R_pekerjaanRepository;
import id.co.askrindo.aos.repository.search.R_pekerjaanSearchRepository;

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
 * Test class for the R_pekerjaanResource REST controller.
 *
 * @see R_pekerjaanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_pekerjaanResourceIntTest {


    private static final Integer DEFAULT_ID_R_PEKERJAAN = 1;
    private static final Integer UPDATED_ID_R_PEKERJAAN = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_pekerjaanRepository r_pekerjaanRepository;

    @Inject
    private R_pekerjaanSearchRepository r_pekerjaanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_pekerjaanMockMvc;

    private R_pekerjaan r_pekerjaan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_pekerjaanResource r_pekerjaanResource = new R_pekerjaanResource();
        ReflectionTestUtils.setField(r_pekerjaanResource, "r_pekerjaanSearchRepository", r_pekerjaanSearchRepository);
        ReflectionTestUtils.setField(r_pekerjaanResource, "r_pekerjaanRepository", r_pekerjaanRepository);
        this.restR_pekerjaanMockMvc = MockMvcBuilders.standaloneSetup(r_pekerjaanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_pekerjaan = new R_pekerjaan();
        r_pekerjaan.setId_r_pekerjaan(DEFAULT_ID_R_PEKERJAAN);
        r_pekerjaan.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_pekerjaan() throws Exception {
        int databaseSizeBeforeCreate = r_pekerjaanRepository.findAll().size();

        // Create the R_pekerjaan

        restR_pekerjaanMockMvc.perform(post("/api/r_pekerjaans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_pekerjaan)))
                .andExpect(status().isCreated());

        // Validate the R_pekerjaan in the database
        List<R_pekerjaan> r_pekerjaans = r_pekerjaanRepository.findAll();
        assertThat(r_pekerjaans).hasSize(databaseSizeBeforeCreate + 1);
        R_pekerjaan testR_pekerjaan = r_pekerjaans.get(r_pekerjaans.size() - 1);
        assertThat(testR_pekerjaan.getId_r_pekerjaan()).isEqualTo(DEFAULT_ID_R_PEKERJAAN);
        assertThat(testR_pekerjaan.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_pekerjaans() throws Exception {
        // Initialize the database
        r_pekerjaanRepository.saveAndFlush(r_pekerjaan);

        // Get all the r_pekerjaans
        restR_pekerjaanMockMvc.perform(get("/api/r_pekerjaans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_pekerjaan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_pekerjaan").value(hasItem(DEFAULT_ID_R_PEKERJAAN)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_pekerjaan() throws Exception {
        // Initialize the database
        r_pekerjaanRepository.saveAndFlush(r_pekerjaan);

        // Get the r_pekerjaan
        restR_pekerjaanMockMvc.perform(get("/api/r_pekerjaans/{id}", r_pekerjaan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_pekerjaan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_pekerjaan").value(DEFAULT_ID_R_PEKERJAAN))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_pekerjaan() throws Exception {
        // Get the r_pekerjaan
        restR_pekerjaanMockMvc.perform(get("/api/r_pekerjaans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_pekerjaan() throws Exception {
        // Initialize the database
        r_pekerjaanRepository.saveAndFlush(r_pekerjaan);

		int databaseSizeBeforeUpdate = r_pekerjaanRepository.findAll().size();

        // Update the r_pekerjaan
        r_pekerjaan.setId_r_pekerjaan(UPDATED_ID_R_PEKERJAAN);
        r_pekerjaan.setUraian(UPDATED_URAIAN);

        restR_pekerjaanMockMvc.perform(put("/api/r_pekerjaans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_pekerjaan)))
                .andExpect(status().isOk());

        // Validate the R_pekerjaan in the database
        List<R_pekerjaan> r_pekerjaans = r_pekerjaanRepository.findAll();
        assertThat(r_pekerjaans).hasSize(databaseSizeBeforeUpdate);
        R_pekerjaan testR_pekerjaan = r_pekerjaans.get(r_pekerjaans.size() - 1);
        assertThat(testR_pekerjaan.getId_r_pekerjaan()).isEqualTo(UPDATED_ID_R_PEKERJAAN);
        assertThat(testR_pekerjaan.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_pekerjaan() throws Exception {
        // Initialize the database
        r_pekerjaanRepository.saveAndFlush(r_pekerjaan);

		int databaseSizeBeforeDelete = r_pekerjaanRepository.findAll().size();

        // Get the r_pekerjaan
        restR_pekerjaanMockMvc.perform(delete("/api/r_pekerjaans/{id}", r_pekerjaan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_pekerjaan> r_pekerjaans = r_pekerjaanRepository.findAll();
        assertThat(r_pekerjaans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
