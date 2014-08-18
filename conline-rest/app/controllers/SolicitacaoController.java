package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import models.*;
import models.repository.*;

import org.hibernate.validator.HibernateValidator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import utils.ConstantUtil;
import utils.PagamentoUtil;
import utils.ToolsUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.validation.*;
import javax.xml.bind.DatatypeConverter;



/**
 * Created by w6c on 09/08/2014.
 */

@Named
@Singleton
public class SolicitacaoController extends Controller{

    private final SolicitacaoRepository solicitacaoRepository;
    private final EstudanteRepository estudanteRepository;
    private final CampusRepository campusRepository;
    private final EstadoRepository estadoRepository;

    public static Validator validator;

    @Inject
    public SolicitacaoController(SolicitacaoRepository solicitacaoRepository, EstudanteRepository estudanteRepository, CampusRepository campusRepository, EstadoRepository estadoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.estudanteRepository = estudanteRepository;
        this.campusRepository = campusRepository;
        this.estadoRepository = estadoRepository;
    }

    public Result saveSolicitacao(){
        JsonNode jsonBodyRequest = request().body().asJson();

        System.out.println("##################################################################");
        System.out.println(jsonBodyRequest.toString());
        System.out.println("##################################################################");


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        Json.setObjectMapper(mapper);

        SolicitacaoModelo solicitacaoRequest = Json.fromJson(jsonBodyRequest, SolicitacaoModelo.class);
        final EstudanteModelo estudanteRequest = solicitacaoRequest.estudante;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        Set<ConstraintViolation<EstudanteModelo>> constraintViolations = validator.validate(estudanteRequest);

        if(!constraintViolations.isEmpty()){

            System.out.println("constraintViolations.size: " + constraintViolations.size());

            ValidationErrorDTO errorDTOAux = ToolsUtil.ConstraintViolation2ValidationErrorDTO(constraintViolations);

            //ObjectNode resultJSON;
            //JsonNode jNode = mapper.valueToTree(errorDTOAux);
            //resultJSON = (ObjectNode) jNode;

            //return badRequest(resultJSON);

            return badRequest(Json.toJson(ToolsUtil
                    .ConstraintViolation2ValidationErrorDTO(constraintViolations).getFieldErrors() )) ;

        }

        final EstudanteModelo estudanteSaved = estudanteRepository.save(estudanteRequest);


        //DEFINICOES SOLICITACAO
        solicitacaoRequest.dataSolicitacao = new DateTime(DateTimeZone.forID("America/Sao_Paulo"));
        solicitacaoRequest.valorCarteira = ConstantUtil.PRECO_CARTEIRINHA;
        solicitacaoRequest.idStatusSolicitacao = ConstantUtil.ID_STATUS_SOL_INICIAL;
        solicitacaoRequest.flagPago = false;
        solicitacaoRequest.codigoLocalEntrega = estudanteRequest.localEntrega;

        solicitacaoRequest.estudante = estudanteRequest;

        //final Usuario usuarioSaved = usuarioRepository.save(usuarioRequest);
        final SolicitacaoModelo solicitacaoSaved = solicitacaoRepository.save(solicitacaoRequest);


        ObjectNode nodeRedirectPagSeguro = Json.newObject();
        nodeRedirectPagSeguro.put(ConstantUtil.KEY_URL_SOLICITACAO_SUCESSO,"http://www.nyc.com");

        return ok(nodeRedirectPagSeguro);
    }


    // ########################################################################################################
    // ########################################### COMBO-LISTS    #############################################
    public Result campusList(){

        final List<Campus> campusList = (List<Campus>) campusRepository.findAll();

        System.out.println("campusRepository - ID" + System.identityHashCode(campusRepository));
        System.out.println("campusList - ID" + System.identityHashCode(campusList));

        return ok(Json.toJson(campusList));
    }

    public Result estadoList(){

        final List<Estado> estadoList = (List<Estado>) estadoRepository.findAll();

        System.out.println("estadoRepository - ID" + System.identityHashCode(estadoRepository));
        System.out.println("estadoList - ID" + System.identityHashCode(estadoList));

        return ok(Json.toJson(estadoList));
    }


    public static Result goPagSeguro(){

        System.out.println("::: goPagSeguro :::");

        //return redirect("http://www.pagseguro.com.br");

        EstudanteModelo estudante = new EstudanteModelo();


        Estado estado = new Estado();
        estado.descricao = "São Paulo";
        estado.sigla = "SP";

        estudante.estado = estado;
        estudante.cidade = "New York City";
        estudante.bairro = "Queens";
        estudante.cep = 40353630;
        estudante.logradouro = "2º Revenue Str";
        estudante.numeroEndereco = "915E";
        estudante.complementoEndereco = "asdasdas";
        estudante.nome = "Emerson Carvalho";
        estudante.email = "emerson@startupland.com";
        estudante.codigo_area_celular = 71;
        estudante.celular = 88008800;

        Long idSolicitacao = 343L;

        String urlPagSeguro = "http://nyc.com";

        urlPagSeguro = efetuarPagamento(getPagamentoPagSeguroPreenchido(String.valueOf(idSolicitacao), estudante));

        return ok("urlPagSeguro: " + urlPagSeguro);
    }

    public static String efetuarPagamento(PagamentoPagSeguro pagamentoPagSeguro) {
        PagamentoUtil pagamentoUtil = new PagamentoUtil();
        return pagamentoUtil.enviaPagamentoPagSeguro(pagamentoPagSeguro);
    }

    public static PagamentoPagSeguro getPagamentoPagSeguroPreenchido(String idSolicitacao, EstudanteModelo estudante) {

        //Estado estadoAux = new Estado();
        //estadoAux.setSigla(estudante.getEstado());
        //estadoAux = getEstadoByExample(estadoAux);

        PagamentoPagSeguro pagamentoPagSeguro = new PagamentoPagSeguro();

        pagamentoPagSeguro.setId(idSolicitacao);
        pagamentoPagSeguro.setDescription(ConstantUtil.PAGSEGURO_DESCRICAO_PRODUTO);
        pagamentoPagSeguro.setQuantity(new Integer(1));
        pagamentoPagSeguro.setAmount(new BigDecimal(ConstantUtil.PAGSEGURO_VALOR));
        pagamentoPagSeguro.setWeight(Long.valueOf(ConstantUtil.PAGSEGURO_PESO));
        pagamentoPagSeguro.setCost(null);
        pagamentoPagSeguro.setReference(idSolicitacao);
        pagamentoPagSeguro.setCountry(ConstantUtil.PAGSEGURO_CODIGO_PAIS);
        pagamentoPagSeguro.setState(estudante.estado.sigla); //pagamentoPagSeguro.setState(estadoAux.getSigla());
        pagamentoPagSeguro.setCity(estudante.cidade);
        pagamentoPagSeguro.setDistrict(estudante.bairro);
        pagamentoPagSeguro.setPostalCode(estudante.cep.toString());
        pagamentoPagSeguro.setStreet(estudante.logradouro);
        pagamentoPagSeguro.setNumber(estudante.numeroEndereco);
        pagamentoPagSeguro.setComplement(estudante.complementoEndereco);
        pagamentoPagSeguro.setName(estudante.nome);
        pagamentoPagSeguro.setEmail(estudante.email);
        pagamentoPagSeguro.setAreaCode(estudante.codigo_area_celular.toString());
        pagamentoPagSeguro.setFone(estudante.celular.toString());

        return pagamentoPagSeguro;
    }


    // ########################################################################################################
    // ############################################## ENABLE CORS #############################################
    private static Result allowCORS() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Max-Age", "300");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        return ok();
    }

    public static Result option() {
        return allowCORS();
    }
}