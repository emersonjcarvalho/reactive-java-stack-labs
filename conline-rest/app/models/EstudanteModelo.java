package models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Created by emerson on 04/08/2014.
 */


@Entity(name = "est_estudante")
@Table(name = "est_estudante")
public class EstudanteModelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EST_ID_ESTUDANTE")
    private Long id;
    @Column(name = "EST_DS_CPF", unique = true, length = 11)
    private String cpf;
    @Column(name = "EST_DS_RG")
    private String rg;
    @Column(name = "EST_DS_NOME")
    private String nome;
    @Column(name = "EST_DS_NOME_EXIBICAO", length = 25)
    private String nomeExibicao;
    @Column(name = "EST_CD_SEXO")
    private Character sexo;
    @Column(name = "EST_DT_NASCIMENTO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "EST_DS_URL_FOTO")
    private String urlFoto;
    @Column(name = "EST_DS_URL_COPIA_DOCUMENTO")
    private String urlCopiaDocumento;
    @Column(name = "EST_DS_URL_COMPROVANTE_ENDERECO")
    private String urlComprovanteEndereco;
    @Column(name = "EST_DS_EMAIL")
    private String email;
    @Column(name = "EST_NU_CODIGO_AREA_TELEFONE")
    private Integer codigo_area_telefone;
    @Column(name = "EST_NU_TELEFONE")
    private Integer telefone;
    @Column(name = "EST_NU_CODIGO_AREA_CELULAR")
    private Integer codigo_area_celular;
    @Column(name = "EST_NU_CELULAR")
    private Integer celular;
    @Column(name = "EST_NU_CEP", length = 8)
    private Integer cep;
    @Column(name = "EST_DS_LOGRADOURO")
    private String logradouro;
    @Column(name = "EST_NU_ENDERECO")
    private String numeroEndereco;
    @Column(name = "EST_DS_COMPLEMENTO_ENDERECO")
    private String complementoEndereco;
    @Column(name = "EST_DS_BAIRRO")
    private String bairro;
    @Column(name = "EST_DS_CIDADE")
    private String cidade;
    @Column(name = "EST_DS_ESTADO")
    private String estado;
    @Column(name = "EST_ID_INSTITUICAO")
    private Long idInstituicao;
    @Column(name = "EST_ID_CAMPUS")
    private String idCampus;
    @Column(name = "EST_DS_CURSO", length = 70)
    private String curso;

    @Column(name = "EST_DS_CURSO_EXIBICAO", length = 25)
    private String cursoExibicao;

    @Column(name = "EST_DS_MATRICULA")
    private String matricula;
    @Column(name = "EST_CD_LOCAL_ENTREGA")
    private Character localEntrega;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estudante")
    private Set<SolicitacaoModelo> listaSolicitacao = new HashSet<SolicitacaoModelo>(0);
    //
    @Column(name = "EST_DS_NOME_ARQ_FOTO")
    private String nomeArquivoFoto;
    //
    @Column(name = "EST_DS_NOME_ARQ_DOCUMENTO")
    private String nomeArquivoDocumento;

    //    @Column(name = "EST_CD_ESTUDANTE")
//    private String cogidoEstudate;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EstudanteModelo other = (EstudanteModelo) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    // GETTERs e SETTERs
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlCopiaDocumento() {
        return urlCopiaDocumento;
    }

    public void setUrlCopiaDocumento(String urlCopiaDocumento) {
        this.urlCopiaDocumento = urlCopiaDocumento;
    }

    public String getUrlComprovanteEndereco() {
        return urlComprovanteEndereco;
    }

    public void setUrlComprovanteEndereco(String urlComprovanteEndereco) {
        this.urlComprovanteEndereco = urlComprovanteEndereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getComplementoEndereco() {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getIdCampus() {
        return idCampus;
    }

    public void setIdCampus(String idCampus) {
        this.idCampus = idCampus;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Character getLocalEntrega() {
        return localEntrega;
    }

    public void setLocalEntrega(Character localEntrega) {
        this.localEntrega = localEntrega;
    }

    public Integer getCodigo_area_telefone() {
        return codigo_area_telefone;
    }

    public void setCodigo_area_telefone(Integer codigo_area_telefone) {
        this.codigo_area_telefone = codigo_area_telefone;
    }

    public Integer getCodigo_area_celular() {
        return codigo_area_celular;
    }

    public void setCodigo_area_celular(Integer codigo_area_celular) {
        this.codigo_area_celular = codigo_area_celular;
    }

    public Set<SolicitacaoModelo> getListaSolicitacao() {
        return listaSolicitacao;
    }

    public void setListaSolicitacao(Set<SolicitacaoModelo> listaSolicitacao) {
        this.listaSolicitacao = listaSolicitacao;
    }

    public String getNomeArquivoFoto() {
        return nomeArquivoFoto;
    }

    public void setNomeArquivoFoto(String nomeArquivoFoto) {
        this.nomeArquivoFoto = nomeArquivoFoto;
    }

    public String getNomeArquivoDocumento() {
        return nomeArquivoDocumento;
    }

    public void setNomeArquivoDocumento(String nomeArquivoDocumento) {
        this.nomeArquivoDocumento = nomeArquivoDocumento;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getCursoExibicao() {
        return cursoExibicao;
    }

    public void setCursoExibicao(String cursoExibicao) {
        this.cursoExibicao = cursoExibicao;
    }
}
