package com.valueit.userdocument.controller;

import com.valueit.userdocument.entity.Document;
import com.valueit.userdocument.entity.User;
import com.valueit.userdocument.exception.FileNotFoundException;
import com.valueit.userdocument.exception.UserNotFoundException;
import com.valueit.userdocument.repository.RepoDocument;
import com.valueit.userdocument.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {
    private final RepoDocument repoDocument ;
    private final RepoUser repoUser;

    @Autowired
    DocumentController(RepoDocument repoDocument, RepoUser repoUser){
        this.repoDocument = repoDocument ;
        this.repoUser = repoUser ;
    }

    @GetMapping(value="/document")
    public List<Document> getAllDocuments(){
        return repoDocument.findAll();
    }

    @GetMapping(path ="/documents/{id}")
    public List<Document> getDocument(@PathVariable("id") Long id) {
        User user= repoUser.findById(id).orElseThrow(()-> new UserNotFoundException(id)) ;
        List<Document> documents = user.getDocuments();
        return documents;
    }



    @GetMapping(value="/document/{name}")
    public Document getDocumentByName(@PathVariable("name") String name) throws Exception{
        Document document = repoDocument.findDocumentByDocName(name);
        if(document == null){
            throw new FileNotFoundException(name) ;
        }
        return document ;
    }
    /*@PostMapping(value="/document")
    public Document addUser(@RequestBody Document document){
        return repoDocument.save(document) ;
    }

    @DeleteMapping(value="/document/{id}")
    void deleteDocument(@PathVariable Long id) {
        repoDocument.deleteById(id);
    }


    @PutMapping(value="/document/{id}")
    public Document replaceDocument(@RequestBody Document newDocument, @PathVariable Long id) {

        return repoDocument.findById(id)
                .map(document -> {
                    document.setDocName(newDocument.getDocName());
                    return repoDocument.save(document);
                })
                .orElseGet(() -> {
                    newDocument.setDocId(id);
                    return repoDocument.save(newDocument);
                });
    }*/
}
