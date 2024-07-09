package com.myproject.Collection.service;



import com.myproject.Collection.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImplement implements FileStorageService{

    private final String UPLOAD_DIR = "D:/SoftWare/SpringBoot/SpringBoot-collection-functions/src/main/uploads";

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    public FileStorageServiceImplement() {

    }

    @Override
    public ResponseEntity<String> saveFile(MultipartFile file) {
/*
        if (file.isEmpty()) {
            System.out.println("no file");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
        }
*/

        try {
            // Create the directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                logger.info("Created upload directory: {}", uploadPath);
                Files.createDirectories(uploadPath);
            }

            // Save the file to the specified directory
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());

            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());

        } catch (IOException e) {
            logger.error("Failed to save file: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
    @Override
    public ResponseEntity<Resource> downloadFile(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.error("File not found: {}", fileName);
                return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            logger.error("Malformed URL exception while downloading file: {}", fileName, e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
