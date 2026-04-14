package com.codeandpray.library.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany(mappedBy = "author")

    private List<Book> books;
    private Long id;

    private String name;
    private String lastname;

    private LocalDate birthDate;
    private LocalDate deathDate;

}