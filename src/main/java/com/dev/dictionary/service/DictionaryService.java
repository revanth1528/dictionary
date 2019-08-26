package com.dev.dictionary.service;

public interface DictionaryService {

    boolean searchInDictionary(String key);


    void buildDictionary(String fileName) throws Exception;

//    void listAllWords();

    void deleteKey(String key);
}
