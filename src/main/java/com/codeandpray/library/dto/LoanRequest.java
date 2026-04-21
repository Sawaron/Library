package com.codeandpray.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private Long bookId;
    private Long readerId;

    private LocalDate returnDate;
}