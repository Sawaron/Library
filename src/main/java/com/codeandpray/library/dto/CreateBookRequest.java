package com.codeandpray.library.dto;

import lombok.Data;

@Data
public class CreateBookRequest {
    private String title;
    private Long authorId;
    private String genre;
    private String isbn;
    private String summary;


}
