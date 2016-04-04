package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_pendidikan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_pendidikan entity.
 */
public interface R_pendidikanSearchRepository extends ElasticsearchRepository<R_pendidikan, Long> {
}
