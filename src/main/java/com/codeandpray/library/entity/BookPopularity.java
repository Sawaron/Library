package com.codeandpray.library.entity;

import com.codeandpray.library.enums.BookPopularityPeriod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_popularities")
@Builder
public class BookPopularity {

    @Id
    @Column(name = "popularity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "read_count", nullable = false)
    private Integer readCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false)
    private BookPopularityPeriod period;

    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt;
}