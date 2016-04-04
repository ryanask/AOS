package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.R_kode_kolektibilitas;
import id.co.askrindo.aos.repository.R_kode_kolektibilitasRepository;
import id.co.askrindo.aos.repository.search.R_kode_kolektibilitasSearchRepository;

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
 * Test class for the R_kode_kolektibilitasResource REST controller.
 *
 * @see R_kode_kolektibilitasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class R_kode_kolektibilitasResourceIntTest {


    private static final Integer DEFAULT_ID_R_KODE_KOLEKTIBILITAS = 1;
    private static final Integer UPDATED_ID_R_KODE_KOLEKTIBILITAS = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private R_kode_kolektibilitasRepository r_kode_kolektibilitasRepository;

    @Inject
    private R_kode_kolektibilitasSearchRepository r_kode_kolektibilitasSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restR_kode_kolektibilitasMockMvc;

    private R_kode_kolektibilitas r_kode_kolektibilitas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        R_kode_kolektibilitasResource r_kode_kolektibilitasResource = new R_kode_kolektibilitasResource();
        ReflectionTestUtils.setField(r_kode_kolektibilitasResource, "r_kode_kolektibilitasSearchRepository", r_kode_kolektibilitasSearchRepository);
        ReflectionTestUtils.setField(r_kode_kolektibilitasResource, "r_kode_kolektibilitasRepository", r_kode_kolektibilitasRepository);
        this.restR_kode_kolektibilitasMockMvc = MockMvcBuilders.standaloneSetup(r_kode_kolektibilitasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        r_kode_kolektibilitas = new R_kode_kolektibilitas();
        r_kode_kolektibilitas.setId_r_kode_kolektibilitas(DEFAULT_ID_R_KODE_KOLEKTIBILITAS);
        r_kode_kolektibilitas.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createR_kode_kolektibilitas() throws Exception {
        int databaseSizeBeforeCreate = r_kode_kolektibilitasRepository.findAll().size();

        // Create the R_kode_kolektibilitas

        restR_kode_kolektibilitasMockMvc.perform(post("/api/r_kode_kolektibilitass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_kode_kolektibilitas)))
                .andExpect(status().isCreated());

        // Validate the R_kode_kolektibilitas in the database
        List<R_kode_kolektibilitas> r_kode_kolektibilitass = r_kode_kolektibilitasRepository.findAll();
        assertThat(r_kode_kolektibilitass).hasSize(databaseSizeBeforeCreate + 1);
        R_kode_kolektibilitas testR_kode_kolektibilitas = r_kode_kolektibilitass.get(r_kode_kolektibilitass.size() - 1);
        assertThat(testR_kode_kolektibilitas.getId_r_kode_kolektibilitas()).isEqualTo(DEFAULT_ID_R_KODE_KOLEKTIBILITAS);
        assertThat(testR_kode_kolektibilitas.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void getAllR_kode_kolektibilitass() throws Exception {
        // Initialize the database
        r_kode_kolektibilitasRepository.saveAndFlush(r_kode_kolektibilitas);

        // Get all the r_kode_kolektibilitass
        restR_kode_kolektibilitasMockMvc.perform(get("/api/r_kode_kolektibilitass?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(r_kode_kolektibilitas.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_kode_kolektibilitas").value(hasItem(DEFAULT_ID_R_KODE_KOLEKTIBILITAS)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getR_kode_kolektibilitas() throws Exception {
        // Initialize the database
        r_kode_kolektibilitasRepository.saveAndFlush(r_kode_kolektibilitas);

        // Get the r_kode_kolektibilitas
        restR_kode_kolektibilitasMockMvc.perform(get("/api/r_kode_kolektibilitass/{id}", r_kode_kolektibilitas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(r_kode_kolektibilitas.getId().intValue()))
            .andExpect(jsonPath("$.id_r_kode_kolektibilitas").value(DEFAULT_ID_R_KODE_KOLEKTIBILITAS))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR_kode_kolektibilitas() throws Exception {
        // Get the r_kode_kolektibilitas
        restR_kode_kolektibilitasMockMvc.perform(get("/api/r_kode_kolektibilitass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR_kode_kolektibilitas() throws Exception {
        // Initialize the database
        r_kode_kolektibilitasRepository.saveAndFlush(r_kode_kolektibilitas);

		int databaseSizeBeforeUpdate = r_kode_kolektibilitasRepository.findAll().size();

        // Update the r_kode_kolektibilitas
        r_kode_kolektibilitas.setId_r_kode_kolektibilitas(UPDATED_ID_R_KODE_KOLEKTIBILITAS);
        r_kode_kolektibilitas.setUraian(UPDATED_URAIAN);

        restR_kode_kolektibilitasMockMvc.perform(put("/api/r_kode_kolektibilitass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(r_kode_kolektibilitas)))
                .andExpect(status().isOk());

        // Validate the R_kode_kolektibilitas in the database
        List<R_kode_kolektibilitas> r_kode_kolektibilitass = r_kode_kolektibilitasRepository.findAll();
        assertThat(r_kode_kolektibilitass).hasSize(databaseSizeBeforeUpdate);
        R_kode_kolektibilitas testR_kode_kolektibilitas = r_kode_kolektibilitass.get(r_kode_kolektibilitass.size() - 1);
        assertThat(testR_kode_kolektibilitas.getId_r_kode_kolektibilitas()).isEqualTo(UPDATED_ID_R_KODE_KOLEKTIBILITAS);
        assertThat(testR_kode_kolektibilitas.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteR_kode_kolektibilitas() throws Exception {
        // Initialize the database
        r_kode_kolektibilitasRepository.saveAndFlush(r_kode_kolektibilitas);

		int databaseSizeBeforeDelete = r_kode_kolektibilitasRepository.findAll().size();

        // Get the r_kode_kolektibilitas
        restR_kode_kolektibilitasMockMvc.perform(delete("/api/r_kode_kolektibilitass/{id}", r_kode_kolektibilitas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<R_kode_kolektibilitas> r_kode_kolektibilitass = r_kode_kolektibilitasRepository.findAll();
        assertThat(r_kode_kolektibilitass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
