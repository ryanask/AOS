package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_jenis_kelamin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_jenis_kelamin entity.
 */
public interface R_jenis_kelaminSearchRepository extends ElasticsearchRepository<R_jenis_kelamin, Long> {
}
