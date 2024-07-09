package com.myproject.Collection.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileStorageService{
    ResponseEntity<String> saveFile(MultipartFile file);
    ResponseEntity<Resource> downloadFile(String fileName);

}
