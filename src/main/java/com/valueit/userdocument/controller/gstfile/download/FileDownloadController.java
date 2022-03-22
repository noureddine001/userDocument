package com.valueit.userdocument.controller.gstfile.download;

import com.valueit.userdocument.entity.Document;
import com.valueit.userdocument.exception.DocumentNotFoundException;
import com.valueit.userdocument.exception.UserNotFoundException;
import com.valueit.userdocument.repository.RepoDocument;
import com.valueit.userdocument.service.FileDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class FileDownloadController {
    private RepoDocument repoDocument ;

    @Autowired
    FileDownloadController(RepoDocument repoDocument){
        this.repoDocument = repoDocument ;
    }

    @GetMapping(path="/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode){
        FileDownloadService downloadUtil = new FileDownloadService();
        Resource resource = null;
        try{
            resource = downloadUtil.getFileAsResource(fileCode);
        }catch(IOException e){
            return ResponseEntity.internalServerError().build();
        }
        if(resource == null){
            return new ResponseEntity<>("File not found" , HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"" ;
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping(value = "/content/id")
    public String getContent(@PathVariable("id") Long id){
        FileDownloadService downloadUtil = new FileDownloadService();
        Document document = repoDocument.findById(id).orElseThrow(()-> new DocumentNotFoundException(id)) ;
        String fileCode = document.getUploadURI().substring(13);;
        Resource resource = null;
        try{
            resource = downloadUtil.getFileAsResource(fileCode);
        }catch(IOException e){
            e.printStackTrace();
        }

        return null ;
    }


}
