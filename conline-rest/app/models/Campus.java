package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by emerson on 04/08/2014.
 */

@Entity
@Table(name = "cam_campus")
public class Campus implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long idInstituicao;
    private String descricao;

    public Campus(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "CAM_ID_CAMPUS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CAM_ID_INSTITUICAO")
    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    @Column(name = "CAM_DS_CAMPUS")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    */

    /*
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campus)) {
            return false;
        }
        Campus other = (Campus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    */

    @Override
    public String toString() {
        return "br.com.carteira.modelo.Campus[id=" + id + "]";
    }
}
