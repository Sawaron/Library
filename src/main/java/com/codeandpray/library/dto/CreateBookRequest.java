package com.codeandpray.library.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreateBookRequest {
    private String title;
    private Set<Long> authorIds;
    private Set<Long> genreIds;
    private String isbn;
    private String summary;
    private String language;
    private int pageCount;
    private int count;


}
