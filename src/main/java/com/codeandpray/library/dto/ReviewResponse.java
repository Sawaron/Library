package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long bookId;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}