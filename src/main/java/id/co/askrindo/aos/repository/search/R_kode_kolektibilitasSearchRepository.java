package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.R_kode_kolektibilitas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the R_kode_kolektibilitas entity.
 */
public interface R_kode_kolektibilitasSearchRepository extends ElasticsearchRepository<R_kode_kolektibilitas, Long> {
}
