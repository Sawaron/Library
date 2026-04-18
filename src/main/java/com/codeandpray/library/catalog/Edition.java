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

    @Column(name = "edition_number")
    private Long editionNumber;

    @Column(name = "edition_publisher")
    private String publisher;

    @Column(name = "edition_publish_date")
    private LocalDate publishDate;
}