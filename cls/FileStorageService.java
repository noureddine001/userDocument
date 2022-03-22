package com.valueit.userdocument.service;


import com.valueit.userdocument.entity.Document;
import com.valueit.userdocument.entity.User;
import com.valueit.userdocument.exception.FileNotFoundException;
import com.valueit.userdocument.exception.FileStorageException;
import com.valueit.userdocument.exception.UserNotFoundException;
import com.valueit.userdocument.property.FileStorageProperties;
import com.valueit.userdocument.repository.RepoDocument;
import com.valueit.userdocument.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    private final RepoDocument repoDocument ;
    private final RepoUser repoUser;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties,
                              RepoUser repoUser,
                              RepoDocument repoDocument ) {
        this.repoDocument = repoDocument;
        this.repoUser = repoUser ;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, Long userId) {
        // Normalize file name

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            User user = repoUser.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            Document document = new Document();
            document.setUser(user);
            document.setDocName(fileName);
            repoDocument.save(document);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }


}
