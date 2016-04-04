package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_is_subsidized;
import id.co.askrindo.aos.repository.R_is_subsidizedRepository;
import id.co.askrindo.aos.repository.search.R_is_subsidizedSearchRepository;

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
 * Test class for the R_is_subsidizedResource REST controller.
 *
 * @see R_is_subsidizedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_is_subsidizedResourceIntTest {


    private static final Integer DEFAULT_ID_R_IS_SUBSIDIZED = 1;
    private static final Integer UPDATED_ID_R_IS_SUBSIDIZED = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_is_subsidizedRepository r_is_subsidizedRepository;

    @Inject
    private R_is_subsidizedSearchRepository r_is_subsidizedSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_is_subsidizedMockMvc;

    private R_is_subsidized r_is_subsidized;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_is_subsidizedResource r_is_subsidizedResource = new R_is_subsidizedResource();
        ReflectionTestUtils.setField(r_is_subsidizedResource, "r_is_subsidizedSearchRepository", r_is_subsidizedSearchRepository);
        ReflectionTestUtils.setField(r_is_subsidizedResource, "r_is_subsidizedRepository", r_is_subsidizedRepository);
        this.restR_is_subsidizedMockMvc = MockMvcBuilders.standaloneSetup(r_is_subsidizedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_is_subsidized = new R_is_subsidized();
        r_is_subsidized.setId_r_is_subsidized(DEFAULT_ID_R_IS_SUBSIDIZED);
        r_is_subsidized.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_is_subsidized() throws Exception {
        int databaseSizeBeforeCreate = r_is_subsidizedRepository.findAll().size();

        // Create the R_is_subsidized

        restR_is_subsidizedMockMvc.perform(post("/api/r_is_subsidizeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_is_subsidized)))
                .andExpect(status().isCreated());

        // Validate the R_is_subsidized in the database
        List<R_is_subsidized> r_is_subsidizeds = r_is_subsidizedRepository.findAll();
        assertThat(r_is_subsidizeds).hasSize(databaseSizeBeforeCreate + 1);
        R_is_subsidized testR_is_subsidized = r_is_subsidizeds.get(r_is_subsidizeds.size() - 1);
        assertThat(testR_is_subsidized.getId_r_is_subsidized()).isEqualTo(DEFAULT_ID_R_IS_SUBSIDIZED);
        assertThat(testR_is_subsidized.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_is_subsidizeds() throws Exception {
        // Initialize the database
        r_is_subsidizedRepository.saveAndFlush(r_is_subsidized);

        // Get all the r_is_subsidizeds
        restR_is_subsidizedMockMvc.perform(get("/api/r_is_subsidizeds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_is_subsidized.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_is_subsidized").value(hasItem(DEFAULT_ID_R_IS_SUBSIDIZED)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_is_subsidized() throws Exception {
        // Initialize the database
        r_is_subsidizedRepository.saveAndFlush(r_is_subsidized);

        // Get the r_is_subsidized
        restR_is_subsidizedMockMvc.perform(get("/api/r_is_subsidizeds/{id}", r_is_subsidized.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_is_subsidized.getId().intValue()))
            .andExpect(jsonPath("$.id_r_is_subsidized").value(DEFAULT_ID_R_IS_SUBSIDIZED))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_is_subsidized() throws Exception {
        // Get the r_is_subsidized
        restR_is_subsidizedMockMvc.perform(get("/api/r_is_subsidizeds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_is_subsidized() throws Exception {
        // Initialize the database
        r_is_subsidizedRepository.saveAndFlush(r_is_subsidized);

		int databaseSizeBeforeUpdate = r_is_subsidizedRepository.findAll().size();

        // Update the r_is_subsidized
        r_is_subsidized.setId_r_is_subsidized(UPDATED_ID_R_IS_SUBSIDIZED);
        r_is_subsidized.setUraian(UPDATED_URAIAN);

        restR_is_subsidizedMockMvc.perform(put("/api/r_is_subsidizeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_is_subsidized)))
                .andExpect(status().isOk());

        // Validate the R_is_subsidized in the database
        List<R_is_subsidized> r_is_subsidizeds = r_is_subsidizedRepository.findAll();
        assertThat(r_is_subsidizeds).hasSize(databaseSizeBeforeUpdate);
        R_is_subsidized testR_is_subsidized = r_is_subsidizeds.get(r_is_subsidizeds.size() - 1);
        assertThat(testR_is_subsidized.getId_r_is_subsidized()).isEqualTo(UPDATED_ID_R_IS_SUBSIDIZED);
        assertThat(testR_is_subsidized.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_is_subsidized() throws Exception {
        // Initialize the database
        r_is_subsidizedRepository.saveAndFlush(r_is_subsidized);

		int databaseSizeBeforeDelete = r_is_subsidizedRepository.findAll().size();

        // Get the r_is_subsidized
        restR_is_subsidizedMockMvc.perform(delete("/api/r_is_subsidizeds/{id}", r_is_subsidized.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_is_subsidized> r_is_subsidizeds = r_is_subsidizedRepository.findAll();
        assertThat(r_is_subsidizeds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
