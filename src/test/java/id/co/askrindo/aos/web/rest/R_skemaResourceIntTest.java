package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_skema;
import id.co.askrindo.aos.repository.R_skemaRepository;
import id.co.askrindo.aos.repository.search.R_skemaSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the R_skemaResource REST controller.
 *
 * @see R_skemaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_skemaResourceIntTest {

    private static final String DEFAULT_ID_R_SKEMA = "AAAAA";
    private static final String UPDATED_ID_R_SKEMA = "BBBBB";
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    private static final BigDecimal DEFAULT_MAX_PLAFON = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAX_PLAFON = new BigDecimal(2);

    private static final Integer DEFAULT_MAX_TENOR = 1;
    private static final Integer UPDATED_MAX_TENOR = 2;

    @Inject
    private R_skemaRepository r_skemaRepository;

    @Inject
    private R_skemaSearchRepository r_skemaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_skemaMockMvc;

    private R_skema r_skema;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_skemaResource r_skemaResource = new R_skemaResource();
        ReflectionTestUtils.setField(r_skemaResource, "r_skemaSearchRepository", r_skemaSearchRepository);
        ReflectionTestUtils.setField(r_skemaResource, "r_skemaRepository", r_skemaRepository);
        this.restR_skemaMockMvc = MockMvcBuilders.standaloneSetup(r_skemaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_skema = new R_skema();
        r_skema.setId_r_skema(DEFAULT_ID_R_SKEMA);
        r_skema.setUraian(DEFAULT_URAIAN);
        r_skema.setMax_plafon(DEFAULT_MAX_PLAFON);
        r_skema.setMax_tenor(DEFAULT_MAX_TENOR);
    }

    @Test
    @Transactional
    public void createR_skema() throws Exception {
        int databaseSizeBeforeCreate = r_skemaRepository.findAll().size();

        // Create the R_skema

        restR_skemaMockMvc.perform(post("/api/r_skemas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_skema)))
                .andExpect(status().isCreated());

        // Validate the R_skema in the database
        List<R_skema> r_skemas = r_skemaRepository.findAll();
        assertThat(r_skemas).hasSize(databaseSizeBeforeCreate + 1);
        R_skema testR_skema = r_skemas.get(r_skemas.size() - 1);
        assertThat(testR_skema.getId_r_skema()).isEqualTo(DEFAULT_ID_R_SKEMA);
        assertThat(testR_skema.getUraian()).isEqualTo(DEFAULT_URAIAN);
        assertThat(testR_skema.getMax_plafon()).isEqualTo(DEFAULT_MAX_PLAFON);
        assertThat(testR_skema.getMax_tenor()).isEqualTo(DEFAULT_MAX_TENOR);
    }

    @Test
    @Transactional
    public void getAllR_skemas() throws Exception {
        // Initialize the database
        r_skemaRepository.saveAndFlush(r_skema);

        // Get all the r_skemas
        restR_skemaMockMvc.perform(get("/api/r_skemas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_skema.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_skema").value(hasItem(DEFAULT_ID_R_SKEMA.toString())))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())))
                .andExpect(jsonPath("$.[*].max_plafon").value(hasItem(DEFAULT_MAX_PLAFON.intValue())))
                .andExpect(jsonPath("$.[*].max_tenor").value(hasItem(DEFAULT_MAX_TENOR)));
    }

    @Test
    @Transactional
    public void getR_skema() throws Exception {
        // Initialize the database
        r_skemaRepository.saveAndFlush(r_skema);

        // Get the r_skema
        restR_skemaMockMvc.perform(get("/api/r_skemas/{id}", r_skema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_skema.getId().intValue()))
            .andExpect(jsonPath("$.id_r_skema").value(DEFAULT_ID_R_SKEMA.toString()))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()))
            .andExpect(jsonPath("$.max_plafon").value(DEFAULT_MAX_PLAFON.intValue()))
            .andExpect(jsonPath("$.max_tenor").value(DEFAULT_MAX_TENOR));
    }

    @Test
    @Transactional
    public void getNonExistingR_skema() throws Exception {
        // Get the r_skema
        restR_skemaMockMvc.perform(get("/api/r_skemas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_skema() throws Exception {
        // Initialize the database
        r_skemaRepository.saveAndFlush(r_skema);

		int databaseSizeBeforeUpdate = r_skemaRepository.findAll().size();

        // Update the r_skema
        r_skema.setId_r_skema(UPDATED_ID_R_SKEMA);
        r_skema.setUraian(UPDATED_URAIAN);
        r_skema.setMax_plafon(UPDATED_MAX_PLAFON);
        r_skema.setMax_tenor(UPDATED_MAX_TENOR);

        restR_skemaMockMvc.perform(put("/api/r_skemas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_skema)))
                .andExpect(status().isOk());

        // Validate the R_skema in the database
        List<R_skema> r_skemas = r_skemaRepository.findAll();
        assertThat(r_skemas).hasSize(databaseSizeBeforeUpdate);
        R_skema testR_skema = r_skemas.get(r_skemas.size() - 1);
        assertThat(testR_skema.getId_r_skema()).isEqualTo(UPDATED_ID_R_SKEMA);
        assertThat(testR_skema.getUraian()).isEqualTo(UPDATED_URAIAN);
        assertThat(testR_skema.getMax_plafon()).isEqualTo(UPDATED_MAX_PLAFON);
        assertThat(testR_skema.getMax_tenor()).isEqualTo(UPDATED_MAX_TENOR);
    }

    @Test
    @Transactional
    public void deleteR_skema() throws Exception {
        // Initialize the database
        r_skemaRepository.saveAndFlush(r_skema);

		int databaseSizeBeforeDelete = r_skemaRepository.findAll().size();

        // Get the r_skema
        restR_skemaMockMvc.perform(delete("/api/r_skemas/{id}", r_skema.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_skema> r_skemas = r_skemaRepository.findAll();
        assertThat(r_skemas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
