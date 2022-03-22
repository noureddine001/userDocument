package com.valueit.userdocument.service;

import com.valueit.userdocument.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadService {

    private Path foundFile;

    public Resource getFileAsResource(String fileCode) throws IOException {
        Path uploadDirectory = Paths.get("files") ;
        Files.list(uploadDirectory).forEach(file ->{
            if(file.getFileName().toString().startsWith(fileCode)){
                foundFile = file ;
                return;
            }
        });

        if(foundFile != null){
            return new UrlResource(foundFile.toUri()) ;
        }

        return null ;
    }


}
