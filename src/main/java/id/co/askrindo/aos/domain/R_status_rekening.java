package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_status_rekening.
 */
@Entity
@Table(name = "r_status_rekening")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_status_rekening")
public class R_status_rekening implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_status_rekening")
    private Integer id_r_status_rekening;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_status_rekening() {
        return id_r_status_rekening;
    }
    
    public void setId_r_status_rekening(Integer id_r_status_rekening) {
        this.id_r_status_rekening = id_r_status_rekening;
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
        R_status_rekening r_status_rekening = (R_status_rekening) o;
        if(r_status_rekening.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_status_rekening.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_status_rekening{" +
            "id=" + id +
            ", id_r_status_rekening='" + id_r_status_rekening + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
