package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditionResponse {
    private Long id;
    private Long bookId;
    private Integer editionNumber;
    private String publisher;
    private String publishDate;
}
