package com.codeandpray.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}