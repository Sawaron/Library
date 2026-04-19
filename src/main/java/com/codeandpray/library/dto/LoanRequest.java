package com.codeandpray.library.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Reader ID is required")
    private Long readerId;

    @Future(message = "Return date must be in the future")
    private LocalDate returnDate;
}