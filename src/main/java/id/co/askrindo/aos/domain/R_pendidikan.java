package id.co.askrindo.aos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A R_pendidikan.
 */
@Entity
@Table(name = "r_pendidikan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "r_pendidikan")
public class R_pendidikan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_r_pendidikan")
    private Integer id_r_pendidikan;
    
    @Column(name = "uraian")
    private String uraian;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_r_pendidikan() {
        return id_r_pendidikan;
    }
    
    public void setId_r_pendidikan(Integer id_r_pendidikan) {
        this.id_r_pendidikan = id_r_pendidikan;
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
        R_pendidikan r_pendidikan = (R_pendidikan) o;
        if(r_pendidikan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, r_pendidikan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "R_pendidikan{" +
            "id=" + id +
            ", id_r_pendidikan='" + id_r_pendidikan + "'" +
            ", uraian='" + uraian + "'" +
            '}';
    }
}
