package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_kode_kolektibilitas.
 */
@Entity
@Table(name = "r_kode_kolektibilitas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_kode_kolektibilitas")
public class R_kode_kolektibilitas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_kode_kolektibilitas")
    private Integer id_r_kode_kolektibilitas;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_kode_kolektibilitas() {
        return id_r_kode_kolektibilitas;
    }
    
    public void setId_r_kode_kolektibilitas(Integer id_r_kode_kolektibilitas) {
        this.id_r_kode_kolektibilitas = id_r_kode_kolektibilitas;
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
        R_kode_kolektibilitas r_kode_kolektibilitas = (R_kode_kolektibilitas) o;
        if(r_kode_kolektibilitas.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_kode_kolektibilitas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_kode_kolektibilitas{" +
            "id=" + id +
            ", id_r_kode_kolektibilitas='" + id_r_kode_kolektibilitas + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
