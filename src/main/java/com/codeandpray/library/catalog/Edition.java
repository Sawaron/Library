package com.codeandpray.library.catalog;

import com.codeandpray.library.entity.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Long editionNumber;
    private String publisher;
    private LocalDate publishDate;
}