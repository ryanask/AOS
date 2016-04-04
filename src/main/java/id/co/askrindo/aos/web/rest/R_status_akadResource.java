package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_status_akad;
import id.co.askrindo.aos.repository.R_status_akadRepository;
import id.co.askrindo.aos.repository.search.R_status_akadSearchRepository;
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
 * REST controller for managing R_status_akad.
 */
@RestController
@RequestMapping("/api")
public class R_status_akadResource {

    private final Logger log = LoggerFactory.getLogger(R_status_akadResource.class);
        
    @Inject
    private R_status_akadRepository r_status_akadRepository;
    
    @Inject
    private R_status_akadSearchRepository r_status_akadSearchRepository;
    
    /**
     * POST  /r_status_akads -> Create a new r_status_akad.
     */
    @RequestMapping(value = "/r_status_akads",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_status_akad> createR_status_akad(@RequestBody R_status_akad r_status_akad) throws URISyntaxException {
        log.debug("REST request to save R_status_akad : {}", r_status_akad);
        if (r_status_akad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_status_akad", "idexists", "A new r_status_akad cannot already have an ID")).body(null);
        }
        R_status_akad result = r_status_akadRepository.save(r_status_akad);
        r_status_akadSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_status_akads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_status_akad", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_status_akads -> Updates an existing r_status_akad.
     */
    @RequestMapping(value = "/r_status_akads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_status_akad> updateR_status_akad(@RequestBody R_status_akad r_status_akad) throws URISyntaxException {
        log.debug("REST request to update R_status_akad : {}", r_status_akad);
        if (r_status_akad.getId() == null) {
            return createR_status_akad(r_status_akad);
        }
        R_status_akad result = r_status_akadRepository.save(r_status_akad);
        r_status_akadSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_status_akad", r_status_akad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_status_akads -> get all the r_status_akads.
     */
    @RequestMapping(value = "/r_status_akads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_status_akad>> getAllR_status_akads(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_status_akads");
        Page<R_status_akad> page = r_status_akadRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_status_akads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_status_akads/:id -> get the "id" r_status_akad.
     */
    @RequestMapping(value = "/r_status_akads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_status_akad> getR_status_akad(@PathVariable Long id) {
        log.debug("REST request to get R_status_akad : {}", id);
        R_status_akad r_status_akad = r_status_akadRepository.findOne(id);
        return Optional.ofNullable(r_status_akad)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_status_akads/:id -> delete the "id" r_status_akad.
     */
    @RequestMapping(value = "/r_status_akads/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_status_akad(@PathVariable Long id) {
        log.debug("REST request to delete R_status_akad : {}", id);
        r_status_akadRepository.delete(id);
        r_status_akadSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_status_akad", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_status_akads/:query -> search for the r_status_akad corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_status_akads/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_status_akad> searchR_status_akads(@PathVariable String query) {
        log.debug("REST request to search R_status_akads for query {}", query);
        return StreamSupport
            .stream(r_status_akadSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
