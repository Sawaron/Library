package com.codeandpray.library.dto;

import com.codeandpray.library.enums.BookPopularityPeriod;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookPopularityResponse {
    private Long id;
    private Long bookId;
    private Integer readCount;
    private BookPopularityPeriod period;
    private LocalDateTime calculatedAt;
}