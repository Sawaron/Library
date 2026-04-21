package com.codeandpray.library.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FineRequest {
    private Long loanId;
    private BigDecimal amount;
    private String reason;
    private String status;
}