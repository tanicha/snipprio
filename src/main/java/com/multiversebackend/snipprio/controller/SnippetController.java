package com.multiversebackend.snipprio.controller;

import com.multiversebackend.snipprio.model.Snippet;
import com.multiversebackend.snipprio.repository.SnippetRepository;
import com.multiversebackend.snipprio.service.SnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController //handles the http request
@RequestMapping("/snippets")
public class SnippetController {

    @Autowired
    private SnippetRepository snippetRepository;
    @Autowired
    private SnippetService snippetService;

//    //when first loaded into the local host
//    @GetMapping
//    public String callGreeting() {
//        return ("Hey welcome, I am Tani!");
//    }

    //gets all snippets
    @GetMapping
    public List<Snippet> getAllSnippets() {
        return snippetRepository.findAll();
    }

    //create a snippet
    @PostMapping
    public ResponseEntity<Snippet> createSnippet(@RequestBody Snippet snippet) {
        try {
            Snippet encryptedSnippet = snippetService.createSnippet(snippet);
            return new ResponseEntity<>(encryptedSnippet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get a snippet by id
    @GetMapping("{id}")
    public ResponseEntity<Snippet> getSnippetById(@PathVariable long id) {
        try {
            Snippet decryptedSnippet = snippetService.getSnippetById(id);
            if (decryptedSnippet != null) {
                return ResponseEntity.ok(decryptedSnippet);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get snippet(s) with requested param content
    @GetMapping("/snippet")
    public List<Snippet> getByParam(@RequestParam("language") String language) {

        List<Snippet> matchingSnippets = new ArrayList<>();

        for (Snippet snippet: snippetRepository.findAll()) {
            String snippetLanguage = snippet.getLanguage();
            if (snippetLanguage != null && snippetLanguage.equals(language)) {
                matchingSnippets.add(snippet);
            }
        }
        return matchingSnippets;
    }
}
