package com.codeandpray.library.entity;

import com.codeandpray.library.enums.AgeCategory;
import com.codeandpray.library.enums.BookStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "author")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Column(nullable = false)
    private String language;

    private float price;

    @Column(name = "has_audiobook")
    private boolean hasAudiobook;

    @Column(name = "reader_time")
    private Integer readerTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_category")
    private AgeCategory ageCategory;

    @Column(unique = true, nullable = false)
    private String isbn;

    private int count;

    @Column(nullable = false, name = "book_genre")
    private String bookGenre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author bookAuthor;


    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
