package com.codeandpray.library.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Long id;
    private Long bookId;
    private Long userId;
    private Integer score;
    private LocalDateTime createdAt;
}