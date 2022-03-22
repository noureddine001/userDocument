package com.valueit.userdocument.service;

import com.valueit.userdocument.entity.Document;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileUploadService {
    public static String saveFile(String fileName, MultipartFile multipartFile)throws IOException{
        Path uploadDirectory = Paths.get("files");
        String fileCode = RandomStringUtils.randomAlphanumeric(8);
        try(InputStream input = multipartFile.getInputStream()){
            Path filePath = uploadDirectory.resolve(fileCode+"-"+fileName) ;
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING) ;
        }catch (IOException ioe){
            throw new IOException("Error saving Uploaded file: " + fileName, ioe) ;
        }
        return fileCode;
    }

    public static List<String> saveMultipleFile(List<String> filesName, List<MultipartFile> multipartFiles){
        return null ;
    }

    public static boolean deleteFile(Document document) throws Exception {
        String pathfile = document.getUploadURI().substring(13);
        Path uploadDirectory = Paths.get("C:\\Users\\noure\\OneDrive\\Bureau\\stagefile\\UserDocument\\files" +pathfile+"-"+document.getDocName() ) ;
        //System.out.println(uploadDirectory);
        boolean isDeleted = Files.deleteIfExists(uploadDirectory) ;
        if(!isDeleted){
            throw new Exception("File not found : " +document.getDocName()) ;
        }
        return isDeleted ;
    }
}
