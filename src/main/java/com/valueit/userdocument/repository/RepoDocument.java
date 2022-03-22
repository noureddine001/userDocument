package com.valueit.userdocument.repository;

import com.valueit.userdocument.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDocument extends JpaRepository<Document, Long > {

    public Document findDocumentByDocName(String name) ;
}
