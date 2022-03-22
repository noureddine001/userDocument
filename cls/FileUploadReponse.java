package com.valueit.userdocument.controller.gstfile.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadReponse {
    private String fileName ;
    private String downloadUrl ;
    private long size ;
}
