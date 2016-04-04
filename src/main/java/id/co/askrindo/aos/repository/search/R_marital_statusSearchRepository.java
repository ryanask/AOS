package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_marital_status;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_marital_status entity.
 */
public interface R_marital_statusSearchRepository extends ElasticsearchRepository<R_marital_status, Long> {
}
