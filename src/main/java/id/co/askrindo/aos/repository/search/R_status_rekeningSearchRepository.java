package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_status_rekening;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_status_rekening entity.
 */
public interface R_status_rekeningSearchRepository extends ElasticsearchRepository<R_status_rekening, Long> {
}
