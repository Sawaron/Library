package com.codeandpray.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequest {
    private Long bookId;
    private Long userId;
    private Integer score;
}