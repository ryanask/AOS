package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_jenis_kelamin;
import id.co.askrindo.aos.repository.R_jenis_kelaminRepository;
import id.co.askrindo.aos.repository.search.R_jenis_kelaminSearchRepository;
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
 * REST controller for managing R_jenis_kelamin.
 */
@RestController
@RequestMapping("/api")
public class R_jenis_kelaminResource {

    private final Logger log = LoggerFactory.getLogger(R_jenis_kelaminResource.class);
        
    @Inject
    private R_jenis_kelaminRepository r_jenis_kelaminRepository;
    
    @Inject
    private R_jenis_kelaminSearchRepository r_jenis_kelaminSearchRepository;
    
    /**
     * POST  /r_jenis_kelamins -> Create a new r_jenis_kelamin.
     */
    @RequestMapping(value = "/r_jenis_kelamins",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_jenis_kelamin> createR_jenis_kelamin(@RequestBody R_jenis_kelamin r_jenis_kelamin) throws URISyntaxException {
        log.debug("REST request to save R_jenis_kelamin : {}", r_jenis_kelamin);
        if (r_jenis_kelamin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_jenis_kelamin", "idexists", "A new r_jenis_kelamin cannot already have an ID")).body(null);
        }
        R_jenis_kelamin result = r_jenis_kelaminRepository.save(r_jenis_kelamin);
        r_jenis_kelaminSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_jenis_kelamins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_jenis_kelamin", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_jenis_kelamins -> Updates an existing r_jenis_kelamin.
     */
    @RequestMapping(value = "/r_jenis_kelamins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_jenis_kelamin> updateR_jenis_kelamin(@RequestBody R_jenis_kelamin r_jenis_kelamin) throws URISyntaxException {
        log.debug("REST request to update R_jenis_kelamin : {}", r_jenis_kelamin);
        if (r_jenis_kelamin.getId() == null) {
            return createR_jenis_kelamin(r_jenis_kelamin);
        }
        R_jenis_kelamin result = r_jenis_kelaminRepository.save(r_jenis_kelamin);
        r_jenis_kelaminSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_jenis_kelamin", r_jenis_kelamin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_jenis_kelamins -> get all the r_jenis_kelamins.
     */
    @RequestMapping(value = "/r_jenis_kelamins",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_jenis_kelamin>> getAllR_jenis_kelamins(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_jenis_kelamins");
        Page<R_jenis_kelamin> page = r_jenis_kelaminRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_jenis_kelamins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_jenis_kelamins/:id -> get the "id" r_jenis_kelamin.
     */
    @RequestMapping(value = "/r_jenis_kelamins/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_jenis_kelamin> getR_jenis_kelamin(@PathVariable Long id) {
        log.debug("REST request to get R_jenis_kelamin : {}", id);
        R_jenis_kelamin r_jenis_kelamin = r_jenis_kelaminRepository.findOne(id);
        return Optional.ofNullable(r_jenis_kelamin)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_jenis_kelamins/:id -> delete the "id" r_jenis_kelamin.
     */
    @RequestMapping(value = "/r_jenis_kelamins/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_jenis_kelamin(@PathVariable Long id) {
        log.debug("REST request to delete R_jenis_kelamin : {}", id);
        r_jenis_kelaminRepository.delete(id);
        r_jenis_kelaminSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_jenis_kelamin", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_jenis_kelamins/:query -> search for the r_jenis_kelamin corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_jenis_kelamins/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_jenis_kelamin> searchR_jenis_kelamins(@PathVariable String query) {
        log.debug("REST request to search R_jenis_kelamins for query {}", query);
        return StreamSupport
            .stream(r_jenis_kelaminSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
