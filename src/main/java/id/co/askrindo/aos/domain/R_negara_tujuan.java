package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_negara_tujuan.
 */
@Entity
@Table(name = "r_negara_tujuan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_negara_tujuan")
public class R_negara_tujuan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_negara_tujuan")
    private Integer id_r_negara_tujuan;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_negara_tujuan() {
        return id_r_negara_tujuan;
    }
    
    public void setId_r_negara_tujuan(Integer id_r_negara_tujuan) {
        this.id_r_negara_tujuan = id_r_negara_tujuan;
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
        R_negara_tujuan r_negara_tujuan = (R_negara_tujuan) o;
        if(r_negara_tujuan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_negara_tujuan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_negara_tujuan{" +
            "id=" + id +
            ", id_r_negara_tujuan='" + id_r_negara_tujuan + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
