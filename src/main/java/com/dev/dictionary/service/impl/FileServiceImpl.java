package com.dev.dictionary.service.impl;

import com.dev.dictionary.service.DictionaryService;
import com.dev.dictionary.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.nio.file.Files;

@Service
public class FileServiceImpl implements FileService  {

    @Autowired
    private DictionaryService dictionaryService;

    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    @Override
        public String storeFile(MultipartFile file) throws  Exception {
            // Normalize file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (!Files.isDirectory(Paths.get(fileStorageLocation)))
            Files.createDirectory(Paths.get(fileStorageLocation));

            try {
                Files.copy(file.getInputStream(), Paths.get(fileStorageLocation+"/"+fileName), StandardCopyOption.REPLACE_EXISTING);
                dictionaryService.buildDictionary(fileName);
                return fileName;
            } catch (IOException ex) {
                throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
            }
        }
    }

