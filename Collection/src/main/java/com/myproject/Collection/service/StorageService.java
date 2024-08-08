package com.myproject.Collection.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    //init a storage file
    void init();

    //store file
    void store(MultipartFile file);

    //load all file path
    Stream<Path> loadAll();

    //combine the rootLocation and the file name to be a path
    Path load(String filename);

    //load the file from the storage location
    Resource loadAsResource(String filename);

    //delete all file from store
    void deleteAll();

}