package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookResponse {
    String title;
    String description;
    String publishDate;
    int pageCount;
    String language;
    float price;
    boolean hasAudiobook;
    Integer readingTime;
    String ageCategory;
    String isbn;
    String genres;
    String bookAuthor;
}