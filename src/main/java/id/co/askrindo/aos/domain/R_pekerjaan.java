package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_pekerjaan.
 */
@Entity
@Table(name = "r_pekerjaan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_pekerjaan")
public class R_pekerjaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_pekerjaan")
    private Integer id_r_pekerjaan;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_pekerjaan() {
        return id_r_pekerjaan;
    }
    
    public void setId_r_pekerjaan(Integer id_r_pekerjaan) {
        this.id_r_pekerjaan = id_r_pekerjaan;
    }

    public String getUraian() {
        return uraian;
    }
    
    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        R_pekerjaan r_pekerjaan = (R_pekerjaan) o;
        if(r_pekerjaan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_pekerjaan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_pekerjaan{" +
            "id=" + id +
            ", id_r_pekerjaan='" + id_r_pekerjaan + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
