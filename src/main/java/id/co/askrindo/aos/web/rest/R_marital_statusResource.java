package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_marital_status;
import id.co.askrindo.aos.repository.R_marital_statusRepository;
import id.co.askrindo.aos.repository.search.R_marital_statusSearchRepository;
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
 * REST controller for managing R_marital_status.
 */
@RestController
@RequestMapping("/api")
public class R_marital_statusResource {

    private final Logger log = LoggerFactory.getLogger(R_marital_statusResource.class);
        
    @Inject
    private R_marital_statusRepository r_marital_statusRepository;
    
    @Inject
    private R_marital_statusSearchRepository r_marital_statusSearchRepository;
    
    /**
     * POST  /r_marital_statuss -> Create a new r_marital_status.
     */
    @RequestMapping(value = "/r_marital_statuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_marital_status> createR_marital_status(@RequestBody R_marital_status r_marital_status) throws URISyntaxException {
        log.debug("REST request to save R_marital_status : {}", r_marital_status);
        if (r_marital_status.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_marital_status", "idexists", "A new r_marital_status cannot already have an ID")).body(null);
        }
        R_marital_status result = r_marital_statusRepository.save(r_marital_status);
        r_marital_statusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_marital_statuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_marital_status", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_marital_statuss -> Updates an existing r_marital_status.
     */
    @RequestMapping(value = "/r_marital_statuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_marital_status> updateR_marital_status(@RequestBody R_marital_status r_marital_status) throws URISyntaxException {
        log.debug("REST request to update R_marital_status : {}", r_marital_status);
        if (r_marital_status.getId() == null) {
            return createR_marital_status(r_marital_status);
        }
        R_marital_status result = r_marital_statusRepository.save(r_marital_status);
        r_marital_statusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_marital_status", r_marital_status.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_marital_statuss -> get all the r_marital_statuss.
     */
    @RequestMapping(value = "/r_marital_statuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_marital_status>> getAllR_marital_statuss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_marital_statuss");
        Page<R_marital_status> page = r_marital_statusRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_marital_statuss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_marital_statuss/:id -> get the "id" r_marital_status.
     */
    @RequestMapping(value = "/r_marital_statuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_marital_status> getR_marital_status(@PathVariable Long id) {
        log.debug("REST request to get R_marital_status : {}", id);
        R_marital_status r_marital_status = r_marital_statusRepository.findOne(id);
        return Optional.ofNullable(r_marital_status)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_marital_statuss/:id -> delete the "id" r_marital_status.
     */
    @RequestMapping(value = "/r_marital_statuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_marital_status(@PathVariable Long id) {
        log.debug("REST request to delete R_marital_status : {}", id);
        r_marital_statusRepository.delete(id);
        r_marital_statusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_marital_status", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_marital_statuss/:query -> search for the r_marital_status corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_marital_statuss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_marital_status> searchR_marital_statuss(@PathVariable String query) {
        log.debug("REST request to search R_marital_statuss for query {}", query);
        return StreamSupport
            .stream(r_marital_statusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
