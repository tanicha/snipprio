package com.multiversebackend.snipprio.controller;

import com.multiversebackend.snipprio.exception.ResourceNotFoundException;
import com.multiversebackend.snipprio.model.Snippet;
import com.multiversebackend.snipprio.repository.SnippetRepository;
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
    public Snippet createSnippet(@RequestBody Snippet snippet) {
        return snippetRepository.save(snippet);
    }

    //get a snippet by id
    @GetMapping("{id}")
    public ResponseEntity<Snippet> getSnippetById(@PathVariable long id) {
        Snippet snippet = snippetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Snippet with id: " + id + "does not exist."));
        return ResponseEntity.ok(snippet);
    }

    //get snippet(s) with requested param content
    @GetMapping("/snippet")
    public List<Snippet> getByParam(@RequestParam("language") String language) {

        List<Snippet> matchingSnippets = new ArrayList<>();

        for (Snippet snippet: snippetRepository.findAll()) {
            if (snippet.getLanguage().equals(language)) {
                matchingSnippets.add(snippet);
            }
        }
        return matchingSnippets;
    }
}
