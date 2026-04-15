package com.codeandpray.library.dto;

import lombok.Data;

@Data
public class CreateBookRequest {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private String summary;


}
