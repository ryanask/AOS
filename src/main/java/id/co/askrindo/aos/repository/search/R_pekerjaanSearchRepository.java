package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_pekerjaan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_pekerjaan entity.
 */
public interface R_pekerjaanSearchRepository extends ElasticsearchRepository<R_pekerjaan, Long> {
}
