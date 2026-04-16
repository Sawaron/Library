package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookReaderResponse {
    String title;
    String genre;
    String author;
    String summary;
    String status;

}
