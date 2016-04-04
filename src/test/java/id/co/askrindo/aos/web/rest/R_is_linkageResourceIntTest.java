package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_is_linkage;
import id.co.askrindo.aos.repository.R_is_linkageRepository;
import id.co.askrindo.aos.repository.search.R_is_linkageSearchRepository;

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
 * Test class for the R_is_linkageResource REST controller.
 *
 * @see R_is_linkageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_is_linkageResourceIntTest {


    private static final Integer DEFAULT_ID_R_IS_LINKAGE = 1;
    private static final Integer UPDATED_ID_R_IS_LINKAGE = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_is_linkageRepository r_is_linkageRepository;

    @Inject
    private R_is_linkageSearchRepository r_is_linkageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_is_linkageMockMvc;

    private R_is_linkage r_is_linkage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_is_linkageResource r_is_linkageResource = new R_is_linkageResource();
        ReflectionTestUtils.setField(r_is_linkageResource, "r_is_linkageSearchRepository", r_is_linkageSearchRepository);
        ReflectionTestUtils.setField(r_is_linkageResource, "r_is_linkageRepository", r_is_linkageRepository);
        this.restR_is_linkageMockMvc = MockMvcBuilders.standaloneSetup(r_is_linkageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_is_linkage = new R_is_linkage();
        r_is_linkage.setId_r_is_linkage(DEFAULT_ID_R_IS_LINKAGE);
        r_is_linkage.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_is_linkage() throws Exception {
        int databaseSizeBeforeCreate = r_is_linkageRepository.findAll().size();

        // Create the R_is_linkage

        restR_is_linkageMockMvc.perform(post("/api/r_is_linkages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_is_linkage)))
                .andExpect(status().isCreated());

        // Validate the R_is_linkage in the database
        List<R_is_linkage> r_is_linkages = r_is_linkageRepository.findAll();
        assertThat(r_is_linkages).hasSize(databaseSizeBeforeCreate + 1);
        R_is_linkage testR_is_linkage = r_is_linkages.get(r_is_linkages.size() - 1);
        assertThat(testR_is_linkage.getId_r_is_linkage()).isEqualTo(DEFAULT_ID_R_IS_LINKAGE);
        assertThat(testR_is_linkage.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_is_linkages() throws Exception {
        // Initialize the database
        r_is_linkageRepository.saveAndFlush(r_is_linkage);

        // Get all the r_is_linkages
        restR_is_linkageMockMvc.perform(get("/api/r_is_linkages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_is_linkage.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_is_linkage").value(hasItem(DEFAULT_ID_R_IS_LINKAGE)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_is_linkage() throws Exception {
        // Initialize the database
        r_is_linkageRepository.saveAndFlush(r_is_linkage);

        // Get the r_is_linkage
        restR_is_linkageMockMvc.perform(get("/api/r_is_linkages/{id}", r_is_linkage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_is_linkage.getId().intValue()))
            .andExpect(jsonPath("$.id_r_is_linkage").value(DEFAULT_ID_R_IS_LINKAGE))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_is_linkage() throws Exception {
        // Get the r_is_linkage
        restR_is_linkageMockMvc.perform(get("/api/r_is_linkages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_is_linkage() throws Exception {
        // Initialize the database
        r_is_linkageRepository.saveAndFlush(r_is_linkage);

		int databaseSizeBeforeUpdate = r_is_linkageRepository.findAll().size();

        // Update the r_is_linkage
        r_is_linkage.setId_r_is_linkage(UPDATED_ID_R_IS_LINKAGE);
        r_is_linkage.setUraian(UPDATED_URAIAN);

        restR_is_linkageMockMvc.perform(put("/api/r_is_linkages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_is_linkage)))
                .andExpect(status().isOk());

        // Validate the R_is_linkage in the database
        List<R_is_linkage> r_is_linkages = r_is_linkageRepository.findAll();
        assertThat(r_is_linkages).hasSize(databaseSizeBeforeUpdate);
        R_is_linkage testR_is_linkage = r_is_linkages.get(r_is_linkages.size() - 1);
        assertThat(testR_is_linkage.getId_r_is_linkage()).isEqualTo(UPDATED_ID_R_IS_LINKAGE);
        assertThat(testR_is_linkage.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_is_linkage() throws Exception {
        // Initialize the database
        r_is_linkageRepository.saveAndFlush(r_is_linkage);

		int databaseSizeBeforeDelete = r_is_linkageRepository.findAll().size();

        // Get the r_is_linkage
        restR_is_linkageMockMvc.perform(delete("/api/r_is_linkages/{id}", r_is_linkage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_is_linkage> r_is_linkages = r_is_linkageRepository.findAll();
        assertThat(r_is_linkages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
