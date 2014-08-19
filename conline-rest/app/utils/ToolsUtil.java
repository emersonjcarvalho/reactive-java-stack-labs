package utils;

import actors.s3aws.S3FileObject;
import akka.actor.ActorRef;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.EstudanteModelo;
import models.FieldErrorDTO;
import models.ValidationErrorDTO;
import play.cache.Cache;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by w6c on 28/07/2014.
 */
public class ToolsUtil {

    //Objecto possibilita ler infos do arquivo application.conf
    final static Config config = ConfigFactory.load();

    final static String accessKey = config.getString("accessKey");
    final static String secretKey = config.getString("secretKey");

    //public final static Config configValidations = ConfigFactory.load("validations");

    //Cliente autenticado do Serviço Amazon AWS
    public static AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

    //#############################   EhCache built-in Play    ############################################

    /**
     *
     * @param contentType
     * @return Extensao do arquivo
     */
    public static String capturaExtensaoDoMimeType(String contentType) {
        //ex contentType = image/jpeg
        String delimiter = "/";
        String extensao = contentType.split(delimiter)[1].toString();

        return extensao.trim().toLowerCase();
    }

    /**
     *
     * @param nomeArquivo
     * @return Extensao do arquivo
     */
    public static String capturaExtensaoNomeArquivo(String nomeArquivo) {
        String delimiter = "\\.";

        String extensao;
        String[] arrayString;
        arrayString = nomeArquivo.split(delimiter);
        int tamanhoArray = arrayString.length; //nomeImagem.split(".").length;
        extensao = arrayString[tamanhoArray - 1].toString(); //nomeImagem.split(".")[tamanhoSplit].trim().toString();

        return extensao.toLowerCase();
    }

    /**
     *
     * @param filePath - Path completo até o arquivo
     * @return Mime Type do arquivo
     */
    public static String detectMimeTypeOfFile(String filePath){

        String fileMimeType =  null;
        try {
            Path pathAux = FileSystems.getDefault().getPath(filePath);

            fileMimeType = Files.probeContentType(pathAux);

            if (fileMimeType == null) {
                System.err.format("'%s' has an" + " unknown filetype.%n", pathAux);
            }

        } catch (IOException x) {
            System.err.println(x);
        }

        return fileMimeType;
    }

    public static ValidationErrorDTO ConstraintViolation2ValidationErrorDTO(Set<ConstraintViolation<EstudanteModelo>> constraintViolations){

        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();

        for (ConstraintViolation<EstudanteModelo> constraintViolation : constraintViolations){

            validationErrorDTO.addFieldError(constraintViolation.getPropertyPath().toString() ,constraintViolation.getMessage());
        }

        return validationErrorDTO;
    }


    public static File getCacheWriteDisk(String nomeFileCache){

        //PEGA FIle do ehCache
        File fileInCache = (File) Cache.get(nomeFileCache);

        System.out.println("(((((((((   ToolsUtil - getCacheWriteDisk - START: " + nomeFileCache);

        System.out.println("(((((((((   ToolsUtil - getCacheWriteDisk - nomeFileCache:  " + nomeFileCache);
        System.out.println("(((((((((   ToolsUtil - getCacheWriteDisk - fileInCache.getName:  " + fileInCache.getName());
        System.out.println();

        if(fileInCache.getName() == nomeFileCache && fileInCache.canRead()){

            System.out.println();
            System.out.println("(((((((((   ToolsUtil - getCacheWriteDisk - nomeFileCache:  " + nomeFileCache);
            System.out.println("(((((((((   ToolsUtil - getCacheWriteDisk - fileInCache.getName:  " + fileInCache.getName());

            return fileInCache;
        }

        //Cria NOVO nome no mesmo diretorio do arquivo do Cache(MultiParti recebido pelo Play no controller)
        String newPath = fileInCache.getParent() + "\\" + nomeFileCache;

        System.out.println("newPath: " + newPath);

        File newFileToInDiks = new File(newPath);

        System.out.println("newFileToInDiks: " + newFileToInDiks.getName());
        System.out.println("newFileToInDiks.canRead: " + newFileToInDiks.canRead());

        //CRIA arquivo com NOVO Nome.. O antigo é automativamente excluido pelo java.io.File
        if(fileInCache.renameTo(newFileToInDiks)){

            System.out.println("fileInCache.renameTo(newFileToInDiks)");

            return newFileToInDiks;

            //#CLEAN-RESOURCE: Limpa arquivo do ehCache
            //Cache.remove(nomeFileFotoCache);
        }else{
            System.out.println("ToolsUtil - getCacheWriteDisk: FALHOU");

            return null;
        }
    }

}
