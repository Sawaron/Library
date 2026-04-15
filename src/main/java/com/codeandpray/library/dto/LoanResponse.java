package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanResponse {

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