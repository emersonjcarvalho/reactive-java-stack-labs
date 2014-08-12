package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import models.*;
import models.repository.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import utils.ConstantUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

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

        System.out.println("####################################");
        System.out.println("estudanteRequest.dataNascimento: " + estudanteRequest.dataNascimento);

        //estudante.setNomeArquivoFoto(nomeArquivoFotoVirtual);
        //estudante.setNomeArquivoDocumento(this.nomeFileDocumento);
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

        return ok(Json.toJson(solicitacaoSaved));
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
