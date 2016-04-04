package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_status_akad.
 */
@Entity
@Table(name = "r_status_akad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_status_akad")
public class R_status_akad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_status_akad")
    private Integer id_r_status_akad;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_status_akad() {
        return id_r_status_akad;
    }
    
    public void setId_r_status_akad(Integer id_r_status_akad) {
        this.id_r_status_akad = id_r_status_akad;
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
        R_status_akad r_status_akad = (R_status_akad) o;
        if(r_status_akad.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_status_akad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_status_akad{" +
            "id=" + id +
            ", id_r_status_akad='" + id_r_status_akad + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
