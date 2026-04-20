package com.codeandpray.library.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long bookId;
    private Long userId;
    private Integer rating;
    private String comment;
}