package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_is_subsidized.
 */
@Entity
@Table(name = "r_is_subsidized")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_is_subsidized")
public class R_is_subsidized implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_is_subsidized")
    private Integer id_r_is_subsidized;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_is_subsidized() {
        return id_r_is_subsidized;
    }
    
    public void setId_r_is_subsidized(Integer id_r_is_subsidized) {
        this.id_r_is_subsidized = id_r_is_subsidized;
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
        R_is_subsidized r_is_subsidized = (R_is_subsidized) o;
        if(r_is_subsidized.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_is_subsidized.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_is_subsidized{" +
            "id=" + id +
            ", id_r_is_subsidized='" + id_r_is_subsidized + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
