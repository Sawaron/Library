package com.codeandpray.library.catalog;

import com.codeandpray.library.entity.Book;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Entity
@Table(name = "editions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "edition_number", nullable = false)
    private Integer editionNumber;

    @Column(nullable = false)
    private String publisher;

    @CreationTimestamp
    @Column(name = "publish_date", updatable = false)
    private LocalDate publishDate;
}