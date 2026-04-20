package com.codeandpray.library.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FineResponse {
    private Long id;
    private Long loanId;
    private BigDecimal amount;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}