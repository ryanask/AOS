package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.R_kode_kolektibilitas;
import id.co.askrindo.aos.repository.R_kode_kolektibilitasRepository;
import id.co.askrindo.aos.repository.search.R_kode_kolektibilitasSearchRepository;
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
 * REST controller for managing R_kode_kolektibilitas.
 */
@RestController
@RequestMapping("/api")
public class R_kode_kolektibilitasResource {

    private final Logger log = LoggerFactory.getLogger(R_kode_kolektibilitasResource.class);
        
    @Inject
    private R_kode_kolektibilitasRepository r_kode_kolektibilitasRepository;
    
    @Inject
    private R_kode_kolektibilitasSearchRepository r_kode_kolektibilitasSearchRepository;
    
    /**
     * POST  /r_kode_kolektibilitass -> Create a new r_kode_kolektibilitas.
     */
    @RequestMapping(value = "/r_kode_kolektibilitass",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_kode_kolektibilitas> createR_kode_kolektibilitas(@RequestBody R_kode_kolektibilitas r_kode_kolektibilitas) throws URISyntaxException {
        log.debug("REST request to save R_kode_kolektibilitas : {}", r_kode_kolektibilitas);
        if (r_kode_kolektibilitas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("r_kode_kolektibilitas", "idexists", "A new r_kode_kolektibilitas cannot already have an ID")).body(null);
        }
        R_kode_kolektibilitas result = r_kode_kolektibilitasRepository.save(r_kode_kolektibilitas);
        r_kode_kolektibilitasSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/r_kode_kolektibilitass/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("r_kode_kolektibilitas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r_kode_kolektibilitass -> Updates an existing r_kode_kolektibilitas.
     */
    @RequestMapping(value = "/r_kode_kolektibilitass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_kode_kolektibilitas> updateR_kode_kolektibilitas(@RequestBody R_kode_kolektibilitas r_kode_kolektibilitas) throws URISyntaxException {
        log.debug("REST request to update R_kode_kolektibilitas : {}", r_kode_kolektibilitas);
        if (r_kode_kolektibilitas.getId() == null) {
            return createR_kode_kolektibilitas(r_kode_kolektibilitas);
        }
        R_kode_kolektibilitas result = r_kode_kolektibilitasRepository.save(r_kode_kolektibilitas);
        r_kode_kolektibilitasSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("r_kode_kolektibilitas", r_kode_kolektibilitas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r_kode_kolektibilitass -> get all the r_kode_kolektibilitass.
     */
    @RequestMapping(value = "/r_kode_kolektibilitass",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<R_kode_kolektibilitas>> getAllR_kode_kolektibilitass(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of R_kode_kolektibilitass");
        Page<R_kode_kolektibilitas> page = r_kode_kolektibilitasRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r_kode_kolektibilitass");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r_kode_kolektibilitass/:id -> get the "id" r_kode_kolektibilitas.
     */
    @RequestMapping(value = "/r_kode_kolektibilitass/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<R_kode_kolektibilitas> getR_kode_kolektibilitas(@PathVariable Long id) {
        log.debug("REST request to get R_kode_kolektibilitas : {}", id);
        R_kode_kolektibilitas r_kode_kolektibilitas = r_kode_kolektibilitasRepository.findOne(id);
        return Optional.ofNullable(r_kode_kolektibilitas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r_kode_kolektibilitass/:id -> delete the "id" r_kode_kolektibilitas.
     */
    @RequestMapping(value = "/r_kode_kolektibilitass/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteR_kode_kolektibilitas(@PathVariable Long id) {
        log.debug("REST request to delete R_kode_kolektibilitas : {}", id);
        r_kode_kolektibilitasRepository.delete(id);
        r_kode_kolektibilitasSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("r_kode_kolektibilitas", id.toString())).build();
    }

    /**
     * SEARCH  /_search/r_kode_kolektibilitass/:query -> search for the r_kode_kolektibilitas corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/r_kode_kolektibilitass/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<R_kode_kolektibilitas> searchR_kode_kolektibilitass(@PathVariable String query) {
        log.debug("REST request to search R_kode_kolektibilitass for query {}", query);
        return StreamSupport
            .stream(r_kode_kolektibilitasSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
