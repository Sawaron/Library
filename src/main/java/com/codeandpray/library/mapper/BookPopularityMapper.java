package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.entity.BookPopularity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookPopularityMapper {

    public BookPopularityResponse toResponse(BookPopularity popularity) {
        if (popularity == null) {
            return null;
        }

        return BookPopularityResponse.builder()
                .id(popularity.getId())
                .bookId(popularity.getBookId())
                .readCount(popularity.getReadCount())
                .period(popularity.getPeriod())
                .calculatedAt(popularity.getCalculatedAt())
                .build();
    }

    public BookPopularity toEntity(BookPopularityRequest request) {
        if (request == null) {
            return null;
        }

        return BookPopularity.builder()
                .bookId(request.getBookId())
                .readCount(request.getReadCount())
                .period(request.getPeriod())
                .calculatedAt(LocalDateTime.now())
                .build();
    }

    public List<BookPopularityResponse> toResponseList(List<BookPopularity> popularities) {
        if (popularities == null) {
            return List.of();
        }

        return popularities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(BookPopularity entity, BookPopularityRequest request) {
        if (entity == null || request == null) {
            return;
        }

        entity.setBookId(request.getBookId());
        entity.setReadCount(request.getReadCount());
        entity.setPeriod(request.getPeriod());
        entity.setCalculatedAt(LocalDateTime.now());
    }
}