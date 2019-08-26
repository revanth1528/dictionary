package com.dev.dictionary.config;

import com.dev.dictionary.service.DictionaryService;
import com.dev.dictionary.util.Trie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.*;

@Configuration
public class InitTrie {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitTrie.class);


    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    @Autowired
    DictionaryService dictionaryService;

    @PostConstruct
    @Bean
    public  void init() throws Exception{
        new Trie();
        DirectoryStream<Path> dirStream = null;
        try {
            if (!Files.isDirectory(Paths.get(fileStorageLocation)))
                Files.createDirectory(Paths.get(fileStorageLocation));


            dirStream = Files.newDirectoryStream(Paths.get(fileStorageLocation),
                    path -> isNotBlank(path.toString()));
            for (Path path : dirStream) {
               dictionaryService.buildDictionary(path.getFileName().toString());
            }
            dirStream.close();
        } catch (IOException e) {
            LOGGER.error("Exception while creating folders : {}", e.getMessage());
        } finally {
            try {
                if (null != dirStream)
                    dirStream.close();
            } catch (IOException e1) {
                LOGGER.info("Exception occured while closing DirectoryStream :{}", e1.getMessage());
            }
        }

    }
}
