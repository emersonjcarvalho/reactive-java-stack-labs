package models;

import java.io.Serializable;
//import java.util.Date;
//import java.sql.Date;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import utils.CustomJsonDateDeserializer;
//import org.joda.time.DateTime;

/**
 * Created by emerson on 04/08/2014.
 */

@Entity(name = "est_estudante")
@Table(name = "est_estudante")
public class EstudanteModelo  {//implements Serializable {

    //public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EST_ID_ESTUDANTE")
    public Long id;

    @Column(name = "EST_DS_CPF", unique = true, length = 11)
    public String cpf;

    @Column(name = "EST_DS_RG")
    public String rg;

    @Column(name = "EST_DS_NOME")
    public String nome;

    @Column(name = "EST_DS_NOME_EXIBICAO", length = 25)
    public String nomeExibicao;

    @Column(name = "EST_CD_SEXO")
    public Character sexo;

    @Column(name = "EST_DT_NASCIMENTO")
    @Temporal(javax.persistence.TemporalType.DATE)
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public Date dataNascimento;

    //@Column(name = "EST_DT_NASCIMENTO")
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    //public DateTime dataNascimento;

    //@Column(name = "EST_DS_URL_FOTO")
    //public String urlFoto;
    //@Column(name = "EST_DS_URL_COPIA_DOCUMENTO")
    //public String urlCopiaDocumento;
    //@Column(name = "EST_DS_URL_COMPROVANTE_ENDERECO")
    //public String urlComprovanteEndereco;

    @Column(name = "EST_DS_EMAIL")
    public String email;

    @Column(name = "EST_NU_CODIGO_AREA_TELEFONE")
    public Integer codigo_area_telefone;

    @Column(name = "EST_NU_TELEFONE")
    public Integer telefone;

    @Column(name = "EST_NU_CODIGO_AREA_CELULAR")
    public Integer codigo_area_celular;

    @Column(name = "EST_NU_CELULAR")
    public Integer celular;

    @Column(name = "EST_NU_CEP", length = 8)
    public Integer cep;

    @Column(name = "EST_DS_LOGRADOURO")
    public String logradouro;

    @Column(name = "EST_NU_ENDERECO")
    public String numeroEndereco;

    @Column(name = "EST_DS_COMPLEMENTO_ENDERECO")
    public String complementoEndereco;

    @Column(name = "EST_DS_BAIRRO")
    public String bairro;

    @Column(name = "EST_DS_CIDADE")
    public String cidade;

    //@Column(name = "EST_DS_ESTADO")
    //public String estado;

    @ManyToOne
    @JoinColumn(name = "EST_ID_ESTADO")
    public Estado estado;

    @Column(name = "EST_ID_INSTITUICAO")
    public Long idInstituicao;

    @ManyToOne
    @JoinColumn(name = "EST_ID_CAMPUS")
    public Campus campus;

    @Column(name = "EST_DS_CURSO", length = 70)
    public String curso;

    @Column(name = "EST_DS_CURSO_EXIBICAO", length = 25)
    public String cursoExibicao;

    @Column(name = "EST_DS_MATRICULA")
    public String matricula;

    @Column(name = "EST_CD_LOCAL_ENTREGA")
    public Character localEntrega;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "estudante")
    //public Set<SolicitacaoModelo> listaSolicitacao = new HashSet<SolicitacaoModelo>(0);

    @Column(name = "EST_DS_NOME_ARQ_FOTO")
    public String nomeArquivoFoto;

    @Column(name = "EST_DS_NOME_ARQ_DOCUMENTO")
    public String nomeArquivoDocumento;

}
