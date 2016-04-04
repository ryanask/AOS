package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_is_linkage;
import id.co.askrindo.aos.repository.R_is_linkageRepository;
import id.co.askrindo.aos.repository.search.R_is_linkageSearchRepository;
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
 * REST controller for managing R_is_linkage.
 */
@RestController
@RequestMapping("/api")
public class R_is_linkageResource {

    private final Logger log = LoggerFactory.getLogger(R_is_linkageResource.class);
        
    @Inject
    private R_is_linkageRepository r_is_linkageRepository;
    
    @Inject
    private R_is_linkageSearchRepository r_is_linkageSearchRepository;
    
    /**
     * POST  /r_is_linkages -> Create a new r_is_linkage.
     */
    @RequestMapping(value = "/r_is_linkages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_is_linkage> createR_is_linkage(@RequestBody R_is_linkage r_is_linkage) throws URISyntaxException {
        log.debug("REST request to save R_is_linkage : {}", r_is_linkage);
        if (r_is_linkage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_is_linkage", "idexists", "A new r_is_linkage cannot already have an ID")).body(null);
        }
        R_is_linkage result = r_is_linkageRepository.save(r_is_linkage);
        r_is_linkageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_is_linkages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_is_linkage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_is_linkages -> Updates an existing r_is_linkage.
     */
    @RequestMapping(value = "/r_is_linkages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_is_linkage> updateR_is_linkage(@RequestBody R_is_linkage r_is_linkage) throws URISyntaxException {
        log.debug("REST request to update R_is_linkage : {}", r_is_linkage);
        if (r_is_linkage.getId() == null) {
            return createR_is_linkage(r_is_linkage);
        }
        R_is_linkage result = r_is_linkageRepository.save(r_is_linkage);
        r_is_linkageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_is_linkage", r_is_linkage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_is_linkages -> get all the r_is_linkages.
     */
    @RequestMapping(value = "/r_is_linkages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_is_linkage>> getAllR_is_linkages(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_is_linkages");
        Page<R_is_linkage> page = r_is_linkageRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_is_linkages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_is_linkages/:id -> get the "id" r_is_linkage.
     */
    @RequestMapping(value = "/r_is_linkages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_is_linkage> getR_is_linkage(@PathVariable Long id) {
        log.debug("REST request to get R_is_linkage : {}", id);
        R_is_linkage r_is_linkage = r_is_linkageRepository.findOne(id);
        return Optional.ofNullable(r_is_linkage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_is_linkages/:id -> delete the "id" r_is_linkage.
     */
    @RequestMapping(value = "/r_is_linkages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_is_linkage(@PathVariable Long id) {
        log.debug("REST request to delete R_is_linkage : {}", id);
        r_is_linkageRepository.delete(id);
        r_is_linkageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_is_linkage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_is_linkages/:query -> search for the r_is_linkage corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_is_linkages/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_is_linkage> searchR_is_linkages(@PathVariable String query) {
        log.debug("REST request to search R_is_linkages for query {}", query);
        return StreamSupport
            .stream(r_is_linkageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
