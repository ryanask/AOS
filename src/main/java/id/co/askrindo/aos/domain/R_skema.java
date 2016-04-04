package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A R_skema.
 */
@Entity
@Table(name = "r_skema")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_skema")
public class R_skema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_skema")
    private String id_r_skema;
    
    @Column(name = "uraian")
    private String uraian;
    
    @Column(name = "max_plafon", precision=10, scale=2)
    private BigDecimal max_plafon;
    
    @Column(name = "max_tenor")
    private Integer max_tenor;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_r_skema() {
        return id_r_skema;
    }
    
    public void setId_r_skema(String id_r_skema) {
        this.id_r_skema = id_r_skema;
    }

    public String getUraian() {
        return uraian;
    }
    
    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public BigDecimal getMax_plafon() {
        return max_plafon;
    }
    
    public void setMax_plafon(BigDecimal max_plafon) {
        this.max_plafon = max_plafon;
    }

    public Integer getMax_tenor() {
        return max_tenor;
    }
    
    public void setMax_tenor(Integer max_tenor) {
        this.max_tenor = max_tenor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        R_skema r_skema = (R_skema) o;
        if(r_skema.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_skema.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_skema{" +
            "id=" + id +
            ", id_r_skema='" + id_r_skema + "'" +
            ", uraian='" + uraian + "'" +
            ", max_plafon='" + max_plafon + "'" +
            ", max_tenor='" + max_tenor + "'" +
            '}';
    }
}
