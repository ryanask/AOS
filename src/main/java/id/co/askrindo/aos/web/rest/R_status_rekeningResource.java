package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_status_rekening;
import id.co.askrindo.aos.repository.R_status_rekeningRepository;
import id.co.askrindo.aos.repository.search.R_status_rekeningSearchRepository;
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
 * REST controller for managing R_status_rekening.
 */
@RestController
@RequestMapping("/api")
public class R_status_rekeningResource {

    private final Logger log = LoggerFactory.getLogger(R_status_rekeningResource.class);
        
    @Inject
    private R_status_rekeningRepository r_status_rekeningRepository;
    
    @Inject
    private R_status_rekeningSearchRepository r_status_rekeningSearchRepository;
    
    /**
     * POST  /r_status_rekenings -> Create a new r_status_rekening.
     */
    @RequestMapping(value = "/r_status_rekenings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_status_rekening> createR_status_rekening(@RequestBody R_status_rekening r_status_rekening) throws URISyntaxException {
        log.debug("REST request to save R_status_rekening : {}", r_status_rekening);
        if (r_status_rekening.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_status_rekening", "idexists", "A new r_status_rekening cannot already have an ID")).body(null);
        }
        R_status_rekening result = r_status_rekeningRepository.save(r_status_rekening);
        r_status_rekeningSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_status_rekenings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_status_rekening", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_status_rekenings -> Updates an existing r_status_rekening.
     */
    @RequestMapping(value = "/r_status_rekenings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_status_rekening> updateR_status_rekening(@RequestBody R_status_rekening r_status_rekening) throws URISyntaxException {
        log.debug("REST request to update R_status_rekening : {}", r_status_rekening);
        if (r_status_rekening.getId() == null) {
            return createR_status_rekening(r_status_rekening);
        }
        R_status_rekening result = r_status_rekeningRepository.save(r_status_rekening);
        r_status_rekeningSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_status_rekening", r_status_rekening.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_status_rekenings -> get all the r_status_rekenings.
     */
    @RequestMapping(value = "/r_status_rekenings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_status_rekening>> getAllR_status_rekenings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_status_rekenings");
        Page<R_status_rekening> page = r_status_rekeningRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_status_rekenings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_status_rekenings/:id -> get the "id" r_status_rekening.
     */
    @RequestMapping(value = "/r_status_rekenings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_status_rekening> getR_status_rekening(@PathVariable Long id) {
        log.debug("REST request to get R_status_rekening : {}", id);
        R_status_rekening r_status_rekening = r_status_rekeningRepository.findOne(id);
        return Optional.ofNullable(r_status_rekening)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_status_rekenings/:id -> delete the "id" r_status_rekening.
     */
    @RequestMapping(value = "/r_status_rekenings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_status_rekening(@PathVariable Long id) {
        log.debug("REST request to delete R_status_rekening : {}", id);
        r_status_rekeningRepository.delete(id);
        r_status_rekeningSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_status_rekening", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_status_rekenings/:query -> search for the r_status_rekening corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_status_rekenings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_status_rekening> searchR_status_rekenings(@PathVariable String query) {
        log.debug("REST request to search R_status_rekenings for query {}", query);
        return StreamSupport
            .stream(r_status_rekeningSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
