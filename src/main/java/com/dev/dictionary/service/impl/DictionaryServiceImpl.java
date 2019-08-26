package com.dev.dictionary.service.impl;

import com.dev.dictionary.service.DictionaryService;
import com.dev.dictionary.util.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DictionaryServiceImpl implements DictionaryService {



    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    @Override
    public boolean searchInDictionary(String key) {
        Trie trie = new Trie();
        return trie.search(key);

    }

    @Override
    public void buildDictionary(String fileName) throws Exception {

        Trie trie = new Trie();


        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileStorageLocation +"/" + fileName), StandardCharsets.UTF_8)) {
            for (String line = null; (line = br.readLine()) != null;) {
                String[] words = line.split(" ");
                for(String word:words){
                    trie.insert(word);
                }
            }
        }

    }


    @Override
    public void deleteKey(String key) {
        Trie trie = new Trie();
        trie.delete(key);

    }
}
