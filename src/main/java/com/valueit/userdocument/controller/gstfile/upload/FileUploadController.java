package com.valueit.userdocument.controller.gstfile.upload;

import com.valueit.userdocument.entity.Document;
import com.valueit.userdocument.entity.User;
import com.valueit.userdocument.exception.DocumentNotFoundException;
import com.valueit.userdocument.exception.UserNotFoundException;
import com.valueit.userdocument.repository.RepoDocument;
import com.valueit.userdocument.repository.RepoUser;
import com.valueit.userdocument.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    private final RepoDocument repoDocument ;
    private final RepoUser repoUser ;

    @Autowired
    FileUploadController(RepoUser repoUser, RepoDocument repoDocument){
        this.repoDocument = repoDocument ;
        this.repoUser = repoUser ;
    }

    @PostMapping("/uploadfile/{id}")
    public ResponseEntity<Document> uploadFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") Long id) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename()) ;
        long size = multipartFile.getSize();
        String fileCode =  FileUploadService.saveFile(filename, multipartFile);
        User user = repoUser.findById(id).orElseThrow(()-> new UserNotFoundException(id)) ;
        Document document = new Document() ;
        document.setUser(user);
        document.setSize(size);
        document.setDocName(filename);
        document.setUploadURI("/downloadFile/" + fileCode);
        repoDocument.save(document) ;
        return new ResponseEntity<>(document, HttpStatus.OK);
    }


    @PostMapping("uploadfiles/{id}")
    public ResponseEntity<List<Document>> uploadFiles(@PathVariable("id") Long id, @RequestParam("file") List<MultipartFile> multipartFiles ){
        User user = repoUser.findById(id).orElseThrow(()-> new UserNotFoundException(id)) ;
        List<Document> documents = new ArrayList<>() ;
        multipartFiles.forEach(multipartFile -> {
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename()) ;
            long size = multipartFile.getSize();
            String fileCode = null;
            try {
                fileCode = FileUploadService.saveFile(filename, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document document = new Document() ;
            document.setUser(user);
            document.setSize(size);
            document.setDocName(filename);
            document.setUploadURI("/downloadFile/" + fileCode);
            documents.add(repoDocument.save(document));
        });
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PostMapping(path="uploadfiledd")
    public ResponseEntity<List<Document>> uploadFiles( @RequestParam("file") List<MultipartFile> multipartFiles ){
        Long id = Long.valueOf(2);
        User user = repoUser.findById(id).orElseThrow(()-> new UserNotFoundException(id)) ;
        List<Document> documents = new ArrayList<>() ;
        multipartFiles.forEach(multipartFile -> {
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename()) ;
            long size = multipartFile.getSize();
            String fileCode = null;
            try {
                fileCode = FileUploadService.saveFile(filename, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document document = new Document() ;
            document.setUser(user);
            document.setSize(size);
            document.setDocName(filename);
            document.setUploadURI("/downloadFile/" + fileCode);
            documents.add(repoDocument.save(document));
        });
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @DeleteMapping("/deletefile/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable("id") Long id ) throws Exception{
        Document document = repoDocument.findById(id).orElseThrow(()-> new DocumentNotFoundException(id)) ;
        Boolean isDeleted = FileUploadService.deleteFile(document) ;
        if(isDeleted){
            repoDocument.delete(document);
            return new ResponseEntity<>("the document is deleted", HttpStatus.OK) ;
        }
        return new ResponseEntity<>("Something is wrong, the file is not deleted", HttpStatus.NOT_FOUND);
}

}
