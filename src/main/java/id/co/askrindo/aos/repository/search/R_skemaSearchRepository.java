package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_skema;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_skema entity.
 */
public interface R_skemaSearchRepository extends ElasticsearchRepository<R_skema, Long> {
}
