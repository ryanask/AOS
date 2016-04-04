package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_skema;
import id.co.askrindo.aos.repository.R_skemaRepository;
import id.co.askrindo.aos.repository.search.R_skemaSearchRepository;
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
 * REST controller for managing R_skema.
 */
@RestController
@RequestMapping("/api")
public class R_skemaResource {

    private final Logger log = LoggerFactory.getLogger(R_skemaResource.class);
        
    @Inject
    private R_skemaRepository r_skemaRepository;
    
    @Inject
    private R_skemaSearchRepository r_skemaSearchRepository;
    
    /**
     * POST  /r_skemas -> Create a new r_skema.
     */
    @RequestMapping(value = "/r_skemas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_skema> createR_skema(@RequestBody R_skema r_skema) throws URISyntaxException {
        log.debug("REST request to save R_skema : {}", r_skema);
        if (r_skema.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_skema", "idexists", "A new r_skema cannot already have an ID")).body(null);
        }
        R_skema result = r_skemaRepository.save(r_skema);
        r_skemaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_skemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_skema", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_skemas -> Updates an existing r_skema.
     */
    @RequestMapping(value = "/r_skemas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_skema> updateR_skema(@RequestBody R_skema r_skema) throws URISyntaxException {
        log.debug("REST request to update R_skema : {}", r_skema);
        if (r_skema.getId() == null) {
            return createR_skema(r_skema);
        }
        R_skema result = r_skemaRepository.save(r_skema);
        r_skemaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_skema", r_skema.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_skemas -> get all the r_skemas.
     */
    @RequestMapping(value = "/r_skemas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_skema>> getAllR_skemas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_skemas");
        Page<R_skema> page = r_skemaRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_skemas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_skemas/:id -> get the "id" r_skema.
     */
    @RequestMapping(value = "/r_skemas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_skema> getR_skema(@PathVariable Long id) {
        log.debug("REST request to get R_skema : {}", id);
        R_skema r_skema = r_skemaRepository.findOne(id);
        return Optional.ofNullable(r_skema)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_skemas/:id -> delete the "id" r_skema.
     */
    @RequestMapping(value = "/r_skemas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_skema(@PathVariable Long id) {
        log.debug("REST request to delete R_skema : {}", id);
        r_skemaRepository.delete(id);
        r_skemaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_skema", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_skemas/:query -> search for the r_skema corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_skemas/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_skema> searchR_skemas(@PathVariable String query) {
        log.debug("REST request to search R_skemas for query {}", query);
        return StreamSupport
            .stream(r_skemaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
