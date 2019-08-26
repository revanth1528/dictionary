package com.dev.dictionary.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String storeFile(MultipartFile file) throws  Exception;
}
