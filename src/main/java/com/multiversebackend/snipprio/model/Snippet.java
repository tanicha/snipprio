package com.multiversebackend.snipprio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "snippets")
public class Snippet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //automatically generate id for the snippets
    private long id;

    @Column(name = "language")
    private String language;

    @Column(name = "code")
    private String code;

}
