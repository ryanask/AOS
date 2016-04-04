package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_status_akad;
import id.co.askrindo.aos.repository.R_status_akadRepository;
import id.co.askrindo.aos.repository.search.R_status_akadSearchRepository;

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
 * Test class for the R_status_akadResource REST controller.
 *
 * @see R_status_akadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_status_akadResourceIntTest {


    private static final Integer DEFAULT_ID_R_STATUS_AKAD = 1;
    private static final Integer UPDATED_ID_R_STATUS_AKAD = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_status_akadRepository r_status_akadRepository;

    @Inject
    private R_status_akadSearchRepository r_status_akadSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_status_akadMockMvc;

    private R_status_akad r_status_akad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_status_akadResource r_status_akadResource = new R_status_akadResource();
        ReflectionTestUtils.setField(r_status_akadResource, "r_status_akadSearchRepository", r_status_akadSearchRepository);
        ReflectionTestUtils.setField(r_status_akadResource, "r_status_akadRepository", r_status_akadRepository);
        this.restR_status_akadMockMvc = MockMvcBuilders.standaloneSetup(r_status_akadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_status_akad = new R_status_akad();
        r_status_akad.setId_r_status_akad(DEFAULT_ID_R_STATUS_AKAD);
        r_status_akad.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_status_akad() throws Exception {
        int databaseSizeBeforeCreate = r_status_akadRepository.findAll().size();

        // Create the R_status_akad

        restR_status_akadMockMvc.perform(post("/api/r_status_akads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_status_akad)))
                .andExpect(status().isCreated());

        // Validate the R_status_akad in the database
        List<R_status_akad> r_status_akads = r_status_akadRepository.findAll();
        assertThat(r_status_akads).hasSize(databaseSizeBeforeCreate + 1);
        R_status_akad testR_status_akad = r_status_akads.get(r_status_akads.size() - 1);
        assertThat(testR_status_akad.getId_r_status_akad()).isEqualTo(DEFAULT_ID_R_STATUS_AKAD);
        assertThat(testR_status_akad.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_status_akads() throws Exception {
        // Initialize the database
        r_status_akadRepository.saveAndFlush(r_status_akad);

        // Get all the r_status_akads
        restR_status_akadMockMvc.perform(get("/api/r_status_akads?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_status_akad.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_status_akad").value(hasItem(DEFAULT_ID_R_STATUS_AKAD)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_status_akad() throws Exception {
        // Initialize the database
        r_status_akadRepository.saveAndFlush(r_status_akad);

        // Get the r_status_akad
        restR_status_akadMockMvc.perform(get("/api/r_status_akads/{id}", r_status_akad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_status_akad.getId().intValue()))
            .andExpect(jsonPath("$.id_r_status_akad").value(DEFAULT_ID_R_STATUS_AKAD))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_status_akad() throws Exception {
        // Get the r_status_akad
        restR_status_akadMockMvc.perform(get("/api/r_status_akads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_status_akad() throws Exception {
        // Initialize the database
        r_status_akadRepository.saveAndFlush(r_status_akad);

		int databaseSizeBeforeUpdate = r_status_akadRepository.findAll().size();

        // Update the r_status_akad
        r_status_akad.setId_r_status_akad(UPDATED_ID_R_STATUS_AKAD);
        r_status_akad.setUraian(UPDATED_URAIAN);

        restR_status_akadMockMvc.perform(put("/api/r_status_akads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_status_akad)))
                .andExpect(status().isOk());

        // Validate the R_status_akad in the database
        List<R_status_akad> r_status_akads = r_status_akadRepository.findAll();
        assertThat(r_status_akads).hasSize(databaseSizeBeforeUpdate);
        R_status_akad testR_status_akad = r_status_akads.get(r_status_akads.size() - 1);
        assertThat(testR_status_akad.getId_r_status_akad()).isEqualTo(UPDATED_ID_R_STATUS_AKAD);
        assertThat(testR_status_akad.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_status_akad() throws Exception {
        // Initialize the database
        r_status_akadRepository.saveAndFlush(r_status_akad);

		int databaseSizeBeforeDelete = r_status_akadRepository.findAll().size();

        // Get the r_status_akad
        restR_status_akadMockMvc.perform(delete("/api/r_status_akads/{id}", r_status_akad.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_status_akad> r_status_akads = r_status_akadRepository.findAll();
        assertThat(r_status_akads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
