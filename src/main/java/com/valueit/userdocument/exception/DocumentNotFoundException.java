package com.valueit.userdocument.exception;

public class DocumentNotFoundException extends RuntimeException{
    public  DocumentNotFoundException(Long id){
        super("Could not find Document" + id);
    }

    public DocumentNotFoundException(String name) {
        super("Could not find Document : "+ name) ;
    }
}
