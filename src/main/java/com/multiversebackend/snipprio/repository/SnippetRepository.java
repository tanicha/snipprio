package com.multiversebackend.snipprio.repository;

import com.multiversebackend.snipprio.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    //will interact with all crud database methods
}
