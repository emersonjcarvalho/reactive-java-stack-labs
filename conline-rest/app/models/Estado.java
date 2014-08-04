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

@Entity(name = "est_estado")
@Table(name = "est_estado")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private String sigla;

    //private List<Cidade> listCidades;
    public Estado() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "est_id_estado")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "est_ds_descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "est_ds_sigla")
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @OneToMany(mappedBy = "estado") public List<Cidade> getListCidades() {
     * return listCidades; }
     *
     * public void setListCidades(List<Cidade> listCidades) { this.listCidades =
     * listCidades; }
     *
     */

    /*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    */

    @Override
    public String toString() {
        return "br.com.solcarteira.modelo.Estado[id=" + id + "]";
    }
}
