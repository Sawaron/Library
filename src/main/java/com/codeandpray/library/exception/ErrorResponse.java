package com.codeandpray.library.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    String code;
    String message;
    LocalDateTime timestamp;

}
