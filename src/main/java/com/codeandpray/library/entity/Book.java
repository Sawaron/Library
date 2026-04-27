package com.codeandpray.library.entity;

import com.codeandpray.library.catalog.Genre;
import com.codeandpray.library.enums.AgeCategory;
import com.codeandpray.library.enums.BookStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"authors", "genres"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String title;

    @Column(length = 1000, columnDefinition = "text")
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Column(nullable = false, columnDefinition = "text")
    private String language;

    private float price;

    @Column(name = "has_audiobook")
    private boolean hasAudiobook;

    @Column(name = "reader_time")
    private Integer readerTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_category")
    private AgeCategory ageCategory;

    @Column(unique = true, nullable = false, columnDefinition = "text")
    private String isbn;

    private int count;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
}