package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import models.*;
import models.repository.*;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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

        System.out.println("#### saveSolicitacao: " + new Date());

        JsonNode jsonBodyRequest = request().body().asJson();
        //final Usuario usuarioRequest = Json.fromJson(jsonBodyRequest, Usuario.class);

        System.out.println(jsonBodyRequest.toString());

        System.out.println("##################################################################");
        System.out.println("##################################################################");

        //ObjectMapper mapper = new ObjectMapper();
        //mapper.registerModule(new JodaModule());
        //Json.setObjectMapper(mapper);

        SolicitacaoModelo solicitacaoRequest = Json.fromJson(jsonBodyRequest, SolicitacaoModelo.class);

        final EstudanteModelo estudanteRequest = solicitacaoRequest.estudante;

        System.out.println("#### estudanteRequest ###");
        System.out.println("estudanteRequest.cpf: " + estudanteRequest.cpf);
        System.out.println("estudanteRequest.estado.id: " + estudanteRequest.estado.id);
        System.out.println("estudanteRequest.dataNascimento: " + estudanteRequest.dataNascimento);
        System.out.println("estudanteRequest.dataNascimento.getTime: " + estudanteRequest.dataNascimento.getTime());
        System.out.println("####################################");

        //Convert ISO 8601(padrao JSON) to DateTime
        //Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime(estudanteRequest.dataNascimento.toString());
        //estudanteRequest.dataNascimento = calendar.getTime();

        //System.out.println("dataNascimentoV2: " + estudanteRequest.dataNascimento);

        /* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        ISO8601DateFormat iso8601DateFormat = new ISO8601DateFormat();

        try {
            Date dateAux = iso8601DateFormat.parse(estudanteRequest.dataNascimento.toString());
            estudanteRequest.dataNascimento = null;

            System.out.println("dateAux: " + dateAux);

            estudanteRequest.dataNascimento = dateAux;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        final EstudanteModelo estudanteSaved = estudanteRepository.save(estudanteRequest);

        //DEFINICOES SOLICITACAO
        solicitacaoRequest.estudante = estudanteRequest;

        //final Usuario usuarioSaved = usuarioRepository.save(usuarioRequest);
        final SolicitacaoModelo solicitacaoSaved = solicitacaoRepository.save(solicitacaoRequest);

        return ok(Json.toJson(solicitacaoSaved));
    }

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
