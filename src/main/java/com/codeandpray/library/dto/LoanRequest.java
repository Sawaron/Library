package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class LoanRequest {

    private Long bookId;
    private String bookTitle;

    private Long readerId;
    private String readerName;

    private LocalDate returnDate;
}