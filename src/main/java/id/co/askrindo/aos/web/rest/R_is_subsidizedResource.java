package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_is_subsidized;
import id.co.askrindo.aos.repository.R_is_subsidizedRepository;
import id.co.askrindo.aos.repository.search.R_is_subsidizedSearchRepository;
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
 * REST controller for managing R_is_subsidized.
 */
@RestController
@RequestMapping("/api")
public class R_is_subsidizedResource {

    private final Logger log = LoggerFactory.getLogger(R_is_subsidizedResource.class);
        
    @Inject
    private R_is_subsidizedRepository r_is_subsidizedRepository;
    
    @Inject
    private R_is_subsidizedSearchRepository r_is_subsidizedSearchRepository;
    
    /**
     * POST  /r_is_subsidizeds -> Create a new r_is_subsidized.
     */
    @RequestMapping(value = "/r_is_subsidizeds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_is_subsidized> createR_is_subsidized(@RequestBody R_is_subsidized r_is_subsidized) throws URISyntaxException {
        log.debug("REST request to save R_is_subsidized : {}", r_is_subsidized);
        if (r_is_subsidized.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_is_subsidized", "idexists", "A new r_is_subsidized cannot already have an ID")).body(null);
        }
        R_is_subsidized result = r_is_subsidizedRepository.save(r_is_subsidized);
        r_is_subsidizedSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_is_subsidizeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_is_subsidized", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_is_subsidizeds -> Updates an existing r_is_subsidized.
     */
    @RequestMapping(value = "/r_is_subsidizeds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_is_subsidized> updateR_is_subsidized(@RequestBody R_is_subsidized r_is_subsidized) throws URISyntaxException {
        log.debug("REST request to update R_is_subsidized : {}", r_is_subsidized);
        if (r_is_subsidized.getId() == null) {
            return createR_is_subsidized(r_is_subsidized);
        }
        R_is_subsidized result = r_is_subsidizedRepository.save(r_is_subsidized);
        r_is_subsidizedSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_is_subsidized", r_is_subsidized.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_is_subsidizeds -> get all the r_is_subsidizeds.
     */
    @RequestMapping(value = "/r_is_subsidizeds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_is_subsidized>> getAllR_is_subsidizeds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_is_subsidizeds");
        Page<R_is_subsidized> page = r_is_subsidizedRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_is_subsidizeds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_is_subsidizeds/:id -> get the "id" r_is_subsidized.
     */
    @RequestMapping(value = "/r_is_subsidizeds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_is_subsidized> getR_is_subsidized(@PathVariable Long id) {
        log.debug("REST request to get R_is_subsidized : {}", id);
        R_is_subsidized r_is_subsidized = r_is_subsidizedRepository.findOne(id);
        return Optional.ofNullable(r_is_subsidized)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_is_subsidizeds/:id -> delete the "id" r_is_subsidized.
     */
    @RequestMapping(value = "/r_is_subsidizeds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_is_subsidized(@PathVariable Long id) {
        log.debug("REST request to delete R_is_subsidized : {}", id);
        r_is_subsidizedRepository.delete(id);
        r_is_subsidizedSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_is_subsidized", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_is_subsidizeds/:query -> search for the r_is_subsidized corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_is_subsidizeds/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_is_subsidized> searchR_is_subsidizeds(@PathVariable String query) {
        log.debug("REST request to search R_is_subsidizeds for query {}", query);
        return StreamSupport
            .stream(r_is_subsidizedSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
