package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_jenis_kelamin;
import id.co.askrindo.aos.repository.R_jenis_kelaminRepository;
import id.co.askrindo.aos.repository.search.R_jenis_kelaminSearchRepository;

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
 * Test class for the R_jenis_kelaminResource REST controller.
 *
 * @see R_jenis_kelaminResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_jenis_kelaminResourceIntTest {


    private static final Integer DEFAULT_ID_R_JENIS_KELAMIN = 1;
    private static final Integer UPDATED_ID_R_JENIS_KELAMIN = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_jenis_kelaminRepository r_jenis_kelaminRepository;

    @Inject
    private R_jenis_kelaminSearchRepository r_jenis_kelaminSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_jenis_kelaminMockMvc;

    private R_jenis_kelamin r_jenis_kelamin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_jenis_kelaminResource r_jenis_kelaminResource = new R_jenis_kelaminResource();
        ReflectionTestUtils.setField(r_jenis_kelaminResource, "r_jenis_kelaminSearchRepository", r_jenis_kelaminSearchRepository);
        ReflectionTestUtils.setField(r_jenis_kelaminResource, "r_jenis_kelaminRepository", r_jenis_kelaminRepository);
        this.restR_jenis_kelaminMockMvc = MockMvcBuilders.standaloneSetup(r_jenis_kelaminResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_jenis_kelamin = new R_jenis_kelamin();
        r_jenis_kelamin.setId_r_jenis_kelamin(DEFAULT_ID_R_JENIS_KELAMIN);
        r_jenis_kelamin.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_jenis_kelamin() throws Exception {
        int databaseSizeBeforeCreate = r_jenis_kelaminRepository.findAll().size();

        // Create the R_jenis_kelamin

        restR_jenis_kelaminMockMvc.perform(post("/api/r_jenis_kelamins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_jenis_kelamin)))
                .andExpect(status().isCreated());

        // Validate the R_jenis_kelamin in the database
        List<R_jenis_kelamin> r_jenis_kelamins = r_jenis_kelaminRepository.findAll();
        assertThat(r_jenis_kelamins).hasSize(databaseSizeBeforeCreate + 1);
        R_jenis_kelamin testR_jenis_kelamin = r_jenis_kelamins.get(r_jenis_kelamins.size() - 1);
        assertThat(testR_jenis_kelamin.getId_r_jenis_kelamin()).isEqualTo(DEFAULT_ID_R_JENIS_KELAMIN);
        assertThat(testR_jenis_kelamin.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_jenis_kelamins() throws Exception {
        // Initialize the database
        r_jenis_kelaminRepository.saveAndFlush(r_jenis_kelamin);

        // Get all the r_jenis_kelamins
        restR_jenis_kelaminMockMvc.perform(get("/api/r_jenis_kelamins?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_jenis_kelamin.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_jenis_kelamin").value(hasItem(DEFAULT_ID_R_JENIS_KELAMIN)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_jenis_kelamin() throws Exception {
        // Initialize the database
        r_jenis_kelaminRepository.saveAndFlush(r_jenis_kelamin);

        // Get the r_jenis_kelamin
        restR_jenis_kelaminMockMvc.perform(get("/api/r_jenis_kelamins/{id}", r_jenis_kelamin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_jenis_kelamin.getId().intValue()))
            .andExpect(jsonPath("$.id_r_jenis_kelamin").value(DEFAULT_ID_R_JENIS_KELAMIN))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_jenis_kelamin() throws Exception {
        // Get the r_jenis_kelamin
        restR_jenis_kelaminMockMvc.perform(get("/api/r_jenis_kelamins/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_jenis_kelamin() throws Exception {
        // Initialize the database
        r_jenis_kelaminRepository.saveAndFlush(r_jenis_kelamin);

		int databaseSizeBeforeUpdate = r_jenis_kelaminRepository.findAll().size();

        // Update the r_jenis_kelamin
        r_jenis_kelamin.setId_r_jenis_kelamin(UPDATED_ID_R_JENIS_KELAMIN);
        r_jenis_kelamin.setUraian(UPDATED_URAIAN);

        restR_jenis_kelaminMockMvc.perform(put("/api/r_jenis_kelamins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_jenis_kelamin)))
                .andExpect(status().isOk());

        // Validate the R_jenis_kelamin in the database
        List<R_jenis_kelamin> r_jenis_kelamins = r_jenis_kelaminRepository.findAll();
        assertThat(r_jenis_kelamins).hasSize(databaseSizeBeforeUpdate);
        R_jenis_kelamin testR_jenis_kelamin = r_jenis_kelamins.get(r_jenis_kelamins.size() - 1);
        assertThat(testR_jenis_kelamin.getId_r_jenis_kelamin()).isEqualTo(UPDATED_ID_R_JENIS_KELAMIN);
        assertThat(testR_jenis_kelamin.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_jenis_kelamin() throws Exception {
        // Initialize the database
        r_jenis_kelaminRepository.saveAndFlush(r_jenis_kelamin);

		int databaseSizeBeforeDelete = r_jenis_kelaminRepository.findAll().size();

        // Get the r_jenis_kelamin
        restR_jenis_kelaminMockMvc.perform(delete("/api/r_jenis_kelamins/{id}", r_jenis_kelamin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_jenis_kelamin> r_jenis_kelamins = r_jenis_kelaminRepository.findAll();
        assertThat(r_jenis_kelamins).hasSize(databaseSizeBeforeDelete - 1);
    }
}
