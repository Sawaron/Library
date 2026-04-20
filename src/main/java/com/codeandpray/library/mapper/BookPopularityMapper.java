package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.entity.BookPopularity;
import org.springframework.stereotype.Component;

@Component
public class BookPopularityMapper {

    public BookPopularityResponse toResponse(BookPopularity popularity) {
        if (popularity == null) return null;

        return BookPopularityResponse.builder()
                .id(popularity.getId())
                .bookId(popularity.getBookId())
                .readCount(popularity.getReadCount())
                .period(popularity.getPeriod())
                .calculatedAt(popularity.getCalculatedAt())
                .build();
    }

    public BookPopularity toEntity(BookPopularityRequest request) {
        if (request == null) return null;

        return BookPopularity.builder()
                .bookId(request.getBookId())
                .readCount(request.getReadCount())
                .period(request.getPeriod())
                // Расчет времени — это логика сервиса популярности
                .build();
    }

    public void updateEntity(BookPopularity entity, BookPopularityRequest request) {
        if (entity == null || request == null) return;

        entity.setBookId(request.getBookId());
        entity.setReadCount(request.getReadCount());
        entity.setPeriod(request.getPeriod());
    }
}