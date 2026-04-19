package com.codeandpray.library.dto;

import com.codeandpray.library.enums.BookPopularityPeriod;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookPopularityRequest {
    private Long bookId;
    private Integer readCount;
    private BookPopularityPeriod period;
}