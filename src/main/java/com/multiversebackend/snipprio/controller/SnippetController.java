package com.multiversebackend.snipprio.controller;

import com.multiversebackend.snipprio.model.Snippet;
import com.multiversebackend.snipprio.repository.SnippetRepository;
import com.multiversebackend.snipprio.service.SnippetService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Snippet> snippets = snippetRepository.findAll();
        List<Snippet> decryptedSnippets = new ArrayList<>();

        for (Snippet snippet : snippets) {
            Snippet decryptedCode = snippetService.decryptSnippet(snippet.getId());
            if (decryptedCode != null) {
                decryptedSnippets.add(snippet);
            }
        }
        return snippetRepository.findAll();
    }

    //creates the keys for my encrypted snippets
    @GetMapping("/createKeys")
    public void createPrivatePublicKeys() {
        snippetService.createKeys();
    }

    //create and encrypt the snippet
    @PostMapping
    public Snippet encryptSnippet(@RequestBody Snippet snippet) {
        return snippetService.encryptSnippet(snippet);
    }

    //get snippet by id
    @GetMapping("{id}")
    public ResponseEntity<Snippet> getSnippetById(@PathVariable long id) {
        Snippet decryptedSnippet = snippetService.decryptSnippet(id);
        if (decryptedSnippet != null) {
            return ResponseEntity.ok(decryptedSnippet);
        } else {
            return ResponseEntity.notFound().build();
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
