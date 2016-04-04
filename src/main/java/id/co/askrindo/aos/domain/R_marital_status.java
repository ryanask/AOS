package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_marital_status.
 */
@Entity
@Table(name = "r_marital_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_marital_status")
public class R_marital_status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_marital_status")
    private Integer id_r_marital_status;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_marital_status() {
        return id_r_marital_status;
    }
    
    public void setId_r_marital_status(Integer id_r_marital_status) {
        this.id_r_marital_status = id_r_marital_status;
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
        R_marital_status r_marital_status = (R_marital_status) o;
        if(r_marital_status.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_marital_status.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_marital_status{" +
            "id=" + id +
            ", id_r_marital_status='" + id_r_marital_status + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
