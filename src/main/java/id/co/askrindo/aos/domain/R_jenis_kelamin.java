package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_jenis_kelamin.
 */
@Entity
@Table(name = "r_jenis_kelamin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_jenis_kelamin")
public class R_jenis_kelamin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_jenis_kelamin")
    private Integer id_r_jenis_kelamin;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_jenis_kelamin() {
        return id_r_jenis_kelamin;
    }
    
    public void setId_r_jenis_kelamin(Integer id_r_jenis_kelamin) {
        this.id_r_jenis_kelamin = id_r_jenis_kelamin;
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
        R_jenis_kelamin r_jenis_kelamin = (R_jenis_kelamin) o;
        if(r_jenis_kelamin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_jenis_kelamin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_jenis_kelamin{" +
            "id=" + id +
            ", id_r_jenis_kelamin='" + id_r_jenis_kelamin + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
