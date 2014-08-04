package models;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Created by emerson on 04/08/2014.
 */


@Entity(name = "sol_solicitacao")
@Table(name = "sol_solicitacao")
public class SolicitacaoModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SOL_ID_SOLICITACAO")
    private Long id;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "SOL_ID_ESTUDANTE", nullable = false)
    private EstudanteModelo estudante;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "SOL_ID_STATUS", nullable = false)
    //private StatusSolicitacaoModelo statusSolicitacao;
    @Column(name = "SOL_ID_STATUS")
    private Long idStatusSolicitacao;

    @Column(name = "SOL_DT_SOLICITACAO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataSolicitacao;

    @Column(name = "SOL_VL_CARTEIRA")
    private Double valorCarteira;

    @Column(name = "SOL_VL_PAGO")
    private Long valorPago;

    @Column(name = "SOL_DT_PAGAMENTO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataPagamento;

    @Column(name = "SOL_FL_PAGO")
    private Boolean flagPago;

    @Column(name = "SOL_ID_FORMA_PAGAMENTO")
    private Long idFormaPagamento;

    @Column(name = "SOL_CD_LOCAL_ENTREGA")
    private Character codigoLocalEntrega;

    // GETTERs e SETTERs
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstudanteModelo getEstudante() {
        return estudante;
    }

    public void setEstudante(EstudanteModelo estudante) {
        this.estudante = estudante;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Double getValorCarteira() {
        return valorCarteira;
    }

    public void setValorCarteira(Double valorCarteira) {
        this.valorCarteira = valorCarteira;
    }

    public Long getValorPago() {
        return valorPago;
    }

    public void setValorPago(Long valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Boolean getFlagPago() {
        return flagPago;
    }

    public void setFlagPago(Boolean flagPago) {
        this.flagPago = flagPago;
    }

    public Long getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(Long idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public Character getCodigoLocalEntrega() {
        return codigoLocalEntrega;
    }

    public void setCodigoLocalEntrega(Character codigoLocalEntrega) {
        this.codigoLocalEntrega = codigoLocalEntrega;
    }

    public Long getIdStatusSolicitacao() {
        return idStatusSolicitacao;
    }

    public void setIdStatusSolicitacao(Long idStatusSolicitacao) {
        this.idStatusSolicitacao = idStatusSolicitacao;
    }

    /*
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SolicitacaoModelo other = (SolicitacaoModelo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    */

    @Override
    public String toString() {
        return "br.com.carteira.modelo.SolicitacaoModelo{" + "id=" + id + '}';
    }

}
