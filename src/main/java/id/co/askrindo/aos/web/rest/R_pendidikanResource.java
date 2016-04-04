package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_pendidikan;
import id.co.askrindo.aos.repository.R_pendidikanRepository;
import id.co.askrindo.aos.repository.search.R_pendidikanSearchRepository;
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
 * REST controller for managing R_pendidikan.
 */
@RestController
@RequestMapping("/api")
public class R_pendidikanResource {

    private final Logger log = LoggerFactory.getLogger(R_pendidikanResource.class);
        
    @Inject
    private R_pendidikanRepository r_pendidikanRepository;
    
    @Inject
    private R_pendidikanSearchRepository r_pendidikanSearchRepository;
    
    /**
     * POST  /r_pendidikans -> Create a new r_pendidikan.
     */
    @RequestMapping(value = "/r_pendidikans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_pendidikan> createR_pendidikan(@RequestBody R_pendidikan r_pendidikan) throws URISyntaxException {
        log.debug("REST request to save R_pendidikan : {}", r_pendidikan);
        if (r_pendidikan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_pendidikan", "idexists", "A new r_pendidikan cannot already have an ID")).body(null);
        }
        R_pendidikan result = r_pendidikanRepository.save(r_pendidikan);
        r_pendidikanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_pendidikans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_pendidikan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_pendidikans -> Updates an existing r_pendidikan.
     */
    @RequestMapping(value = "/r_pendidikans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_pendidikan> updateR_pendidikan(@RequestBody R_pendidikan r_pendidikan) throws URISyntaxException {
        log.debug("REST request to update R_pendidikan : {}", r_pendidikan);
        if (r_pendidikan.getId() == null) {
            return createR_pendidikan(r_pendidikan);
        }
        R_pendidikan result = r_pendidikanRepository.save(r_pendidikan);
        r_pendidikanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_pendidikan", r_pendidikan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_pendidikans -> get all the r_pendidikans.
     */
    @RequestMapping(value = "/r_pendidikans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_pendidikan>> getAllR_pendidikans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_pendidikans");
        Page<R_pendidikan> page = r_pendidikanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_pendidikans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_pendidikans/:id -> get the "id" r_pendidikan.
     */
    @RequestMapping(value = "/r_pendidikans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_pendidikan> getR_pendidikan(@PathVariable Long id) {
        log.debug("REST request to get R_pendidikan : {}", id);
        R_pendidikan r_pendidikan = r_pendidikanRepository.findOne(id);
        return Optional.ofNullable(r_pendidikan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_pendidikans/:id -> delete the "id" r_pendidikan.
     */
    @RequestMapping(value = "/r_pendidikans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_pendidikan(@PathVariable Long id) {
        log.debug("REST request to delete R_pendidikan : {}", id);
        r_pendidikanRepository.delete(id);
        r_pendidikanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_pendidikan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_pendidikans/:query -> search for the r_pendidikan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_pendidikans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_pendidikan> searchR_pendidikans(@PathVariable String query) {
        log.debug("REST request to search R_pendidikans for query {}", query);
        return StreamSupport
            .stream(r_pendidikanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
