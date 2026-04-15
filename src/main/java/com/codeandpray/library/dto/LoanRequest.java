package com.codeandpray.library.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanRequest {

    private Long bookId;
    private String bookTitle;

    private Long readerId;
    private String readerName;

    private LocalDate returnDate;
}