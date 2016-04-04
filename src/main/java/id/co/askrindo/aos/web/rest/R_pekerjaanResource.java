package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_pekerjaan;
import id.co.askrindo.aos.repository.R_pekerjaanRepository;
import id.co.askrindo.aos.repository.search.R_pekerjaanSearchRepository;
import id.co.askrindo.aos.web.rest.util.HeaderUtil;
import id.co.askrindo.aos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing R_pekerjaan.
 */
@RestController
@RequestMapping("/api")
public class R_pekerjaanResource {

    private final Logger log = LoggerFactory.getLogger(R_pekerjaanResource.class);
        
    @Inject
    private R_pekerjaanRepository r_pekerjaanRepository;
    
    @Inject
    private R_pekerjaanSearchRepository r_pekerjaanSearchRepository;
    
    /**
     * POST  /r_pekerjaans -> Create a new r_pekerjaan.
     */
    @RequestMapping(value = "/r_pekerjaans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_pekerjaan> createR_pekerjaan(@RequestBody R_pekerjaan r_pekerjaan) throws URISyntaxException {
        log.debug("REST request to save R_pekerjaan : {}", r_pekerjaan);
        if (r_pekerjaan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_pekerjaan", "idexists", "A new r_pekerjaan cannot already have an ID")).body(null);
        }
        R_pekerjaan result = r_pekerjaanRepository.save(r_pekerjaan);
        r_pekerjaanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_pekerjaans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_pekerjaan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_pekerjaans -> Updates an existing r_pekerjaan.
     */
    @RequestMapping(value = "/r_pekerjaans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_pekerjaan> updateR_pekerjaan(@RequestBody R_pekerjaan r_pekerjaan) throws URISyntaxException {
        log.debug("REST request to update R_pekerjaan : {}", r_pekerjaan);
        if (r_pekerjaan.getId() == null) {
            return createR_pekerjaan(r_pekerjaan);
        }
        R_pekerjaan result = r_pekerjaanRepository.save(r_pekerjaan);
        r_pekerjaanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_pekerjaan", r_pekerjaan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_pekerjaans -> get all the r_pekerjaans.
     */
    @RequestMapping(value = "/r_pekerjaans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_pekerjaan>> getAllR_pekerjaans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_pekerjaans");
        Page<R_pekerjaan> page = r_pekerjaanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_pekerjaans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_pekerjaans/:id -> get the "id" r_pekerjaan.
     */
    @RequestMapping(value = "/r_pekerjaans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_pekerjaan> getR_pekerjaan(@PathVariable Long id) {
        log.debug("REST request to get R_pekerjaan : {}", id);
        R_pekerjaan r_pekerjaan = r_pekerjaanRepository.findOne(id);
        return Optional.ofNullable(r_pekerjaan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_pekerjaans/:id -> delete the "id" r_pekerjaan.
     */
    @RequestMapping(value = "/r_pekerjaans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_pekerjaan(@PathVariable Long id) {
        log.debug("REST request to delete R_pekerjaan : {}", id);
        r_pekerjaanRepository.delete(id);
        r_pekerjaanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_pekerjaan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_pekerjaans/:query -> search for the r_pekerjaan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_pekerjaans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_pekerjaan> searchR_pekerjaans(@PathVariable String query) {
        log.debug("REST request to search R_pekerjaans for query {}", query);
        return StreamSupport
            .stream(r_pekerjaanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
