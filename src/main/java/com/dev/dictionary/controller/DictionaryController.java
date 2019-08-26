package com.dev.dictionary.controller;


import com.dev.dictionary.data.Meta;
import com.dev.dictionary.service.DictionaryService;
import com.dev.dictionary.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DictionaryController {

    @Autowired
    private FileService fileService;

    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping("/uploadFile")
    public ResponseEntity<Meta> uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        String fileName = fileService.storeFile(file);

        Meta meta = new Meta();
        meta.setStatus(0);
        meta.setDescription("File is " + fileName + "Upload Successfully");
        return ResponseEntity.ok().body(meta);
    }

//    @GetMapping("/list")
//    public ResponseEntity<?> getAllWords(){
//        dictionaryService.listAllWords();
//    }

    @DeleteMapping("/deleteKey/{key}")
    public ResponseEntity<?> deleteKey(@PathVariable("key") String key){
        boolean flag = dictionaryService.searchInDictionary(key);
         if(flag){ dictionaryService.deleteKey(key);
             return ResponseEntity.ok().body("Deleted Successfully");
         }
        return ResponseEntity.ok().body("Word not found in Dictionary");
    }


    @GetMapping("/search/{key}")
    public ResponseEntity<?> searchInDictionary(@PathVariable("key") String key) throws Exception{
        boolean flag = dictionaryService.searchInDictionary(key);

        if(flag){
            return ResponseEntity.ok().body("Word Exists in Dictionary");
        }
        return ResponseEntity.ok().body("Word not found in Dictionary");

    }


}
