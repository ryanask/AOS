package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_negara_tujuan;
import id.co.askrindo.aos.repository.R_negara_tujuanRepository;
import id.co.askrindo.aos.repository.search.R_negara_tujuanSearchRepository;

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
 * Test class for the R_negara_tujuanResource REST controller.
 *
 * @see R_negara_tujuanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_negara_tujuanResourceIntTest {


    private static final Integer DEFAULT_ID_R_NEGARA_TUJUAN = 1;
    private static final Integer UPDATED_ID_R_NEGARA_TUJUAN = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_negara_tujuanRepository r_negara_tujuanRepository;

    @Inject
    private R_negara_tujuanSearchRepository r_negara_tujuanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_negara_tujuanMockMvc;

    private R_negara_tujuan r_negara_tujuan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_negara_tujuanResource r_negara_tujuanResource = new R_negara_tujuanResource();
        ReflectionTestUtils.setField(r_negara_tujuanResource, "r_negara_tujuanSearchRepository", r_negara_tujuanSearchRepository);
        ReflectionTestUtils.setField(r_negara_tujuanResource, "r_negara_tujuanRepository", r_negara_tujuanRepository);
        this.restR_negara_tujuanMockMvc = MockMvcBuilders.standaloneSetup(r_negara_tujuanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_negara_tujuan = new R_negara_tujuan();
        r_negara_tujuan.setId_r_negara_tujuan(DEFAULT_ID_R_NEGARA_TUJUAN);
        r_negara_tujuan.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_negara_tujuan() throws Exception {
        int databaseSizeBeforeCreate = r_negara_tujuanRepository.findAll().size();

        // Create the R_negara_tujuan

        restR_negara_tujuanMockMvc.perform(post("/api/r_negara_tujuans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_negara_tujuan)))
                .andExpect(status().isCreated());

        // Validate the R_negara_tujuan in the database
        List<R_negara_tujuan> r_negara_tujuans = r_negara_tujuanRepository.findAll();
        assertThat(r_negara_tujuans).hasSize(databaseSizeBeforeCreate + 1);
        R_negara_tujuan testR_negara_tujuan = r_negara_tujuans.get(r_negara_tujuans.size() - 1);
        assertThat(testR_negara_tujuan.getId_r_negara_tujuan()).isEqualTo(DEFAULT_ID_R_NEGARA_TUJUAN);
        assertThat(testR_negara_tujuan.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_negara_tujuans() throws Exception {
        // Initialize the database
        r_negara_tujuanRepository.saveAndFlush(r_negara_tujuan);

        // Get all the r_negara_tujuans
        restR_negara_tujuanMockMvc.perform(get("/api/r_negara_tujuans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_negara_tujuan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_negara_tujuan").value(hasItem(DEFAULT_ID_R_NEGARA_TUJUAN)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_negara_tujuan() throws Exception {
        // Initialize the database
        r_negara_tujuanRepository.saveAndFlush(r_negara_tujuan);

        // Get the r_negara_tujuan
        restR_negara_tujuanMockMvc.perform(get("/api/r_negara_tujuans/{id}", r_negara_tujuan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_negara_tujuan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_negara_tujuan").value(DEFAULT_ID_R_NEGARA_TUJUAN))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_negara_tujuan() throws Exception {
        // Get the r_negara_tujuan
        restR_negara_tujuanMockMvc.perform(get("/api/r_negara_tujuans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_negara_tujuan() throws Exception {
        // Initialize the database
        r_negara_tujuanRepository.saveAndFlush(r_negara_tujuan);

		int databaseSizeBeforeUpdate = r_negara_tujuanRepository.findAll().size();

        // Update the r_negara_tujuan
        r_negara_tujuan.setId_r_negara_tujuan(UPDATED_ID_R_NEGARA_TUJUAN);
        r_negara_tujuan.setUraian(UPDATED_URAIAN);

        restR_negara_tujuanMockMvc.perform(put("/api/r_negara_tujuans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_negara_tujuan)))
                .andExpect(status().isOk());

        // Validate the R_negara_tujuan in the database
        List<R_negara_tujuan> r_negara_tujuans = r_negara_tujuanRepository.findAll();
        assertThat(r_negara_tujuans).hasSize(databaseSizeBeforeUpdate);
        R_negara_tujuan testR_negara_tujuan = r_negara_tujuans.get(r_negara_tujuans.size() - 1);
        assertThat(testR_negara_tujuan.getId_r_negara_tujuan()).isEqualTo(UPDATED_ID_R_NEGARA_TUJUAN);
        assertThat(testR_negara_tujuan.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_negara_tujuan() throws Exception {
        // Initialize the database
        r_negara_tujuanRepository.saveAndFlush(r_negara_tujuan);

		int databaseSizeBeforeDelete = r_negara_tujuanRepository.findAll().size();

        // Get the r_negara_tujuan
        restR_negara_tujuanMockMvc.perform(delete("/api/r_negara_tujuans/{id}", r_negara_tujuan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_negara_tujuan> r_negara_tujuans = r_negara_tujuanRepository.findAll();
        assertThat(r_negara_tujuans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
