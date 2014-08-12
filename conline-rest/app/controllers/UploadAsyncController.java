package controllers;

import play.cache.Cache;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.ConstantUtil;
import utils.ToolsUtil;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by emerson on 11/08/2014.
 */

@Named
@Singleton
public class UploadAsyncController extends Controller {


    /**
     * 1 - Get File of MultiPart Body
     * 2 - Formart name of file to Persiste in S3
     * 3 - Save Nama Format and File in ehCache
     * @param cpf and File in MultiPart
     * @return nomeFileFotoCache asJson - Nome do arquivo com CPF e extensao. Nome Pronto p/ S3
     *
     */
    public static Result fotoFileUpload(String cpf) {
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
            System.out.println("<<<<<<<< fotoFileUpload - ELSE = (picture != null) >>>>>>>>>>>>>");

            //flash("error", "Missing file");
            //return redirect(controllers.routes.Application.index());
            return badRequest("<<<<<<<< fotoFileUpload - ELSE = (picture != null) >>>>>>>>>>>>>");
        }
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

    public static Result optionID(Long id) {
        return allowCORS();
    }

}
