package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_negara_tujuan;
import id.co.askrindo.aos.repository.R_negara_tujuanRepository;
import id.co.askrindo.aos.repository.search.R_negara_tujuanSearchRepository;
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
 * REST controller for managing R_negara_tujuan.
 */
@RestController
@RequestMapping("/api")
public class R_negara_tujuanResource {

    private final Logger log = LoggerFactory.getLogger(R_negara_tujuanResource.class);
        
    @Inject
    private R_negara_tujuanRepository r_negara_tujuanRepository;
    
    @Inject
    private R_negara_tujuanSearchRepository r_negara_tujuanSearchRepository;
    
    /**
     * POST  /r_negara_tujuans -> Create a new r_negara_tujuan.
     */
    @RequestMapping(value = "/r_negara_tujuans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_negara_tujuan> createR_negara_tujuan(@RequestBody R_negara_tujuan r_negara_tujuan) throws URISyntaxException {
        log.debug("REST request to save R_negara_tujuan : {}", r_negara_tujuan);
        if (r_negara_tujuan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_negara_tujuan", "idexists", "A new r_negara_tujuan cannot already have an ID")).body(null);
        }
        R_negara_tujuan result = r_negara_tujuanRepository.save(r_negara_tujuan);
        r_negara_tujuanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_negara_tujuans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_negara_tujuan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_negara_tujuans -> Updates an existing r_negara_tujuan.
     */
    @RequestMapping(value = "/r_negara_tujuans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_negara_tujuan> updateR_negara_tujuan(@RequestBody R_negara_tujuan r_negara_tujuan) throws URISyntaxException {
        log.debug("REST request to update R_negara_tujuan : {}", r_negara_tujuan);
        if (r_negara_tujuan.getId() == null) {
            return createR_negara_tujuan(r_negara_tujuan);
        }
        R_negara_tujuan result = r_negara_tujuanRepository.save(r_negara_tujuan);
        r_negara_tujuanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_negara_tujuan", r_negara_tujuan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_negara_tujuans -> get all the r_negara_tujuans.
     */
    @RequestMapping(value = "/r_negara_tujuans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_negara_tujuan>> getAllR_negara_tujuans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_negara_tujuans");
        Page<R_negara_tujuan> page = r_negara_tujuanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_negara_tujuans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_negara_tujuans/:id -> get the "id" r_negara_tujuan.
     */
    @RequestMapping(value = "/r_negara_tujuans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_negara_tujuan> getR_negara_tujuan(@PathVariable Long id) {
        log.debug("REST request to get R_negara_tujuan : {}", id);
        R_negara_tujuan r_negara_tujuan = r_negara_tujuanRepository.findOne(id);
        return Optional.ofNullable(r_negara_tujuan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_negara_tujuans/:id -> delete the "id" r_negara_tujuan.
     */
    @RequestMapping(value = "/r_negara_tujuans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_negara_tujuan(@PathVariable Long id) {
        log.debug("REST request to delete R_negara_tujuan : {}", id);
        r_negara_tujuanRepository.delete(id);
        r_negara_tujuanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_negara_tujuan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_negara_tujuans/:query -> search for the r_negara_tujuan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_negara_tujuans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_negara_tujuan> searchR_negara_tujuans(@PathVariable String query) {
        log.debug("REST request to search R_negara_tujuans for query {}", query);
        return StreamSupport
            .stream(r_negara_tujuanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
