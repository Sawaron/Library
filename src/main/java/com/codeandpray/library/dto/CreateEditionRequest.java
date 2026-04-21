package com.codeandpray.library.dto;

import lombok.Data;

@Data
public class CreateEditionRequest {
    private Long bookId;
    private Integer editionNumber;
    private String publisher;
}
