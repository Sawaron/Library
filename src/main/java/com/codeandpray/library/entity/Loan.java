package com.codeandpray.library.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private String bookTitle;

    private Long readerId;
    private String readerName;

    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;

    private String status;
}