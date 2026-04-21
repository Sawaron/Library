package com.codeandpray.library.dto;

import lombok.Data;

@Data
public class UpdateEditionRequest {
    private Integer editionNumber;
    private String publisher;
}
