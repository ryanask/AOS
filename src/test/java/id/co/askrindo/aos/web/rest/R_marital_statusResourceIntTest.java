package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_marital_status;
import id.co.askrindo.aos.repository.R_marital_statusRepository;
import id.co.askrindo.aos.repository.search.R_marital_statusSearchRepository;

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
 * Test class for the R_marital_statusResource REST controller.
 *
 * @see R_marital_statusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_marital_statusResourceIntTest {


    private static final Integer DEFAULT_ID_R_MARITAL_STATUS = 1;
    private static final Integer UPDATED_ID_R_MARITAL_STATUS = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_marital_statusRepository r_marital_statusRepository;

    @Inject
    private R_marital_statusSearchRepository r_marital_statusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_marital_statusMockMvc;

    private R_marital_status r_marital_status;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_marital_statusResource r_marital_statusResource = new R_marital_statusResource();
        ReflectionTestUtils.setField(r_marital_statusResource, "r_marital_statusSearchRepository", r_marital_statusSearchRepository);
        ReflectionTestUtils.setField(r_marital_statusResource, "r_marital_statusRepository", r_marital_statusRepository);
        this.restR_marital_statusMockMvc = MockMvcBuilders.standaloneSetup(r_marital_statusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_marital_status = new R_marital_status();
        r_marital_status.setId_r_marital_status(DEFAULT_ID_R_MARITAL_STATUS);
        r_marital_status.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_marital_status() throws Exception {
        int databaseSizeBeforeCreate = r_marital_statusRepository.findAll().size();

        // Create the R_marital_status

        restR_marital_statusMockMvc.perform(post("/api/r_marital_statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_marital_status)))
                .andExpect(status().isCreated());

        // Validate the R_marital_status in the database
        List<R_marital_status> r_marital_statuss = r_marital_statusRepository.findAll();
        assertThat(r_marital_statuss).hasSize(databaseSizeBeforeCreate + 1);
        R_marital_status testR_marital_status = r_marital_statuss.get(r_marital_statuss.size() - 1);
        assertThat(testR_marital_status.getId_r_marital_status()).isEqualTo(DEFAULT_ID_R_MARITAL_STATUS);
        assertThat(testR_marital_status.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_marital_statuss() throws Exception {
        // Initialize the database
        r_marital_statusRepository.saveAndFlush(r_marital_status);

        // Get all the r_marital_statuss
        restR_marital_statusMockMvc.perform(get("/api/r_marital_statuss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_marital_status.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_marital_status").value(hasItem(DEFAULT_ID_R_MARITAL_STATUS)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_marital_status() throws Exception {
        // Initialize the database
        r_marital_statusRepository.saveAndFlush(r_marital_status);

        // Get the r_marital_status
        restR_marital_statusMockMvc.perform(get("/api/r_marital_statuss/{id}", r_marital_status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_marital_status.getId().intValue()))
            .andExpect(jsonPath("$.id_r_marital_status").value(DEFAULT_ID_R_MARITAL_STATUS))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_marital_status() throws Exception {
        // Get the r_marital_status
        restR_marital_statusMockMvc.perform(get("/api/r_marital_statuss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_marital_status() throws Exception {
        // Initialize the database
        r_marital_statusRepository.saveAndFlush(r_marital_status);

		int databaseSizeBeforeUpdate = r_marital_statusRepository.findAll().size();

        // Update the r_marital_status
        r_marital_status.setId_r_marital_status(UPDATED_ID_R_MARITAL_STATUS);
        r_marital_status.setUraian(UPDATED_URAIAN);

        restR_marital_statusMockMvc.perform(put("/api/r_marital_statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_marital_status)))
                .andExpect(status().isOk());

        // Validate the R_marital_status in the database
        List<R_marital_status> r_marital_statuss = r_marital_statusRepository.findAll();
        assertThat(r_marital_statuss).hasSize(databaseSizeBeforeUpdate);
        R_marital_status testR_marital_status = r_marital_statuss.get(r_marital_statuss.size() - 1);
        assertThat(testR_marital_status.getId_r_marital_status()).isEqualTo(UPDATED_ID_R_MARITAL_STATUS);
        assertThat(testR_marital_status.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_marital_status() throws Exception {
        // Initialize the database
        r_marital_statusRepository.saveAndFlush(r_marital_status);

		int databaseSizeBeforeDelete = r_marital_statusRepository.findAll().size();

        // Get the r_marital_status
        restR_marital_statusMockMvc.perform(delete("/api/r_marital_statuss/{id}", r_marital_status.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_marital_status> r_marital_statuss = r_marital_statusRepository.findAll();
        assertThat(r_marital_statuss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
