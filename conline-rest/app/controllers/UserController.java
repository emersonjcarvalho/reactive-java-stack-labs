package controllers;

//import actors.ActorHelper;
//import actors.mail.EmailConfirmacaoMessage;
//import actors.mail.EmailOperacionalMessage;
import com.fasterxml.jackson.databind.JsonNode;
import models.Grupo;
import models.Usuario;
import models.UsuarioRepository;
import models.GrupoRepository;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.util.Date;
import java.util.List;

//import services.EmailService;

/**
 * Created by w6c on 04/08/2014.
 */


@Named
@Singleton
public class UserController extends Controller{


    //MUDAR P/ INJECAO DE DEPENDENCIA
    //final EmailService emailService = new EmailService(ActorHelper.getActorSystem());

    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;

    @Inject
    public UserController(UsuarioRepository usuarioRepository, GrupoRepository grupoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
    }

    public Result hello(){

        Grupo operadores = new Grupo("Operadores");

        grupoRepository.save(operadores);

        final Usuario usuario = new Usuario();
        usuario.id = 2L;
        usuario.nome = "Beatriz Carvalho";
        usuario.login = "bia.carvalho";
        usuario.senha = "1234";

        usuario.grupo = operadores;

        usuarioRepository.save(usuario);

        System.out.println("");
        System.out.println("usuario.nome: " + usuario.nome);
        System.out.println("usuario.grupo.descricao: " + usuario.grupo.descricao);

        return ok(play.libs.Json.toJson(usuario));
    }

    public Result getAllUsers(){

        List<Usuario> usuarioList = (List<Usuario>) usuarioRepository.findAll();

        return ok(Json.toJson(usuarioList));
    }

    public Result getUserById(Long id){
        return ok(Json.toJson("getUserById - ID: " + id));
    }

    public Result saveUser(){
        JsonNode jsonBodyRequest = request().body().asJson();
        final Usuario usuarioRequest = Json.fromJson(jsonBodyRequest, Usuario.class);

        return ok(Json.toJson(usuarioRequest));
    }

    public Result updateUser(Long id){

        JsonNode jsonFromRequest = request().body().asJson();
        Usuario usuario = play.libs.Json.fromJson(jsonFromRequest, Usuario.class);
        usuario.id = id;

        return ok(play.libs.Json.toJson(usuario));
    }


    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$      Testes Email        $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    /*
    public Result simpleEmail(String to){

        String subject = "TestMail - ACTOR Commons-Mail";
        String MsgTextBody = "TExto Alternativo";
        String MsgHtmlBody = "This is a test mail ... :-) TO: " + to;

        File attachementTest = new File("C:\\z800.jpg");

        EmailConfirmacaoMessage emailConfirmacaoPOJO = new EmailConfirmacaoMessage(subject,MsgTextBody,MsgHtmlBody, to);

        EmailOperacionalMessage emailOperacionalPojo =
                new EmailOperacionalMessage("Assunto Operacional", "Alternative text Operacional", "HTML operacional", "", attachementTest, attachementTest);

        emailService.sendEmailConfirmacao(emailConfirmacaoPOJO);

        if(attachementTest.exists()){
            emailService.sendEmailOperacional(emailOperacionalPojo);
            return ok(play.libs.Json.toJson("Message send to Actor for Email: " +to + " at  " + new Date()));
        }else{
            return notFound("File not found");
        }
    }
    */

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

    public static Result optionID(Long id) {
        return allowCORS();
    }
}
