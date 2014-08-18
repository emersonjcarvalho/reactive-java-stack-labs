package controllers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import actors.mail.EmailNotificacaoMessage;
import actors.mail.EmailOperacionalMessage;

import services.MailServiceHelper;
import services.StorageServiceHelper;
import utils.ConstantUtil;
import utils.ToolsUtil;
import views.html.*;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class Application extends Controller {

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
    // ########################################################################################################

    public static Result index() {

        for(int i=1;  i<=20; i++){
            System.out.println("");
        }

        System.out.println("Application - index");
        System.out.println("");

        return ok(index.render("Your new application is ready."));
    }

    public static Result blank(){

        for(int i=1;  i<=20; i++){
            System.out.println("");
        }

        System.out.println("Application - blank");
        System.out.println("");

        return ok(play.libs.Json.toJson("blank-blank-blank-blank-blank-blank-blank-blank"));
    }

    public Result ses(String to){
        String subject = "Confirmacao - " + to + " - " + new Date();
        String MsgTextBody = "TExto Alternativo";
        String MsgHtmlBody = "Confirmacao - " + to + " - " + new Date();


        String subjectOperacional = "Operacional - " + to + " - " + new Date();
        String MsgTextBodyOperacional = "TExto Operacional";
        String MsgHtmlBodyOperacional = "Operacional - " + to + " - " + new Date();
        File attachementTest = new File("C:\\z800.jpg");

        String resultStr = "to: " + to+ " - " + new Date();

        System.out.println(resultStr);

        EmailNotificacaoMessage notificacaoMessage = new EmailNotificacaoMessage(subject, MsgTextBody, MsgHtmlBody, to);
        EmailOperacionalMessage operacionalMessage = new EmailOperacionalMessage(subjectOperacional, MsgTextBodyOperacional, MsgHtmlBodyOperacional, attachementTest, attachementTest);


        //supervisorRef.tell(new OperacionalMailPOJO(to), supervisorRef);
        //mailServiceHelper.sendMailOperacional(operacionalMailPOJO);
        MailServiceHelper.sendMailOperacional(operacionalMessage);

        //supervisorRef.tell(notificacaoMailPOJO, supervisorRef);
        //mailServiceHelper.sendMailNotificacao(notificacaoMailPOJO);
        MailServiceHelper.sendMailNotificacao(notificacaoMessage);

        return ok(resultStr);
    }

    //%%%%%%%%%%%%%%%%%%%%
    public static Result s3(String nomeFileFotoCache) {

        StorageServiceHelper storageService = new StorageServiceHelper();
        //storageService.putObjectS3(s3FileObject);

        storageService.salvarFotoStorage(nomeFileFotoCache);

        return ok("Application - S3 - PUT: " + " - " + new Date());
    }

    public static Result getCache(String key){

        File objRetrive = (File) Cache.get(key);

        if(objRetrive != null){

            System.out.println("<<<<<<<< getCache -  fileName: " + objRetrive.getName());
            System.out.println("<<<<<<<< getCache -  getPath: " + objRetrive.getPath());
            System.out.println("<<<<<<<< getCache -  canRead: " + objRetrive.canRead());
            System.out.println("<<<<<<<< getCache -  objRetrive.length: " + objRetrive.length());

            return ok("objRetrive.Name: " + objRetrive.getName() + " | " + "objRetrive.getParent: " + objRetrive.getParent() + " | objRetrive.length: "+ objRetrive.length());

        }else{
            return notFound("OBJ [" + key + "] NOT FOUND!!");
        }
    }

    public static Result apagaCache(String key){

        Cache.remove(key);

        return ok("Object: " + key + " - Excluido com sucesso");
    }

    /**
     * 1 - Get File of MultiPart Body
     * 2 - Formart name of file to Persiste in S3
     * 3 - Save Nama Format and File in ehCache
     * @param cpf and File in MultiPart
     * @return nomeFileFotoCache asJson - Nome do arquivo com CPF e extensao. Nome Pronto p/ S3
     *
     */
    public static Result uploadFoto(String cpf) {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile(ConstantUtil.KEY_MULTIPARTI_FILE_UPLOAD_FOTO);

        if (picture != null) {
            String extensao =  ToolsUtil.capturaExtensaoDoMimeType(picture.getContentType());
            String nomeFileFotoCache = ConstantUtil.PREFIX_FOTO + cpf + "." + extensao;
            //File file = picture.getFile();
            Cache.set(nomeFileFotoCache, picture.getFile());

            String contentType = picture.getContentType();
            System.out.println("<<<<<<<< uploadFoto -  contentType: " + contentType);

            System.out.println("<<<<<<<< capturaExtensaoDoMimeType: " + extensao);

            //return ok(file).as("image/jpeg");
            return ok(Json.toJson(Json.newObject().put("nomeFileFotoCache", nomeFileFotoCache)));

        } else {
            System.out.println("<<<<<<<< uploadFoto - ELSE  >>>>>>>>>>>>>");

            flash("error", "Missing file");
            return redirect(controllers.routes.Application.index());
        }
    }

    public static Result properties(){

        // "config1" is just an example of using a file other than
        // application.conf
        Config config1 = ConfigFactory.load("validations");

        // use the config ourselves
        System.out.println("config1, complex-app.something="
                + config1.getString("complex-app.something"));


        return ok("config.validations: " + config1.getString("complex-app.something"));
    }
}
