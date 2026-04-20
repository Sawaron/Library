package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.entity.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public RatingResponse toResponse(Rating rating) {
        if (rating == null) return null;

        return RatingResponse.builder()
                .id(rating.getId())
                .bookId(rating.getBookId())
                .userId(rating.getUserId())
                .score(rating.getScore())
                .createdAt(rating.getCreatedAt())
                .build();
    }

    public Rating toEntity(RatingRequest request) {
        if (request == null) return null;

        return Rating.builder()
                .bookId(request.getBookId())
                .userId(request.getUserId())
                .score(request.getScore())
                // Дату createdAt лучше ставить в сервисе или через JPA @CreatedDate
                .build();
    }

    public void updateEntity(Rating rating, RatingRequest request) {
        if (rating == null || request == null) return;

        rating.setBookId(request.getBookId());
        rating.setUserId(request.getUserId());
        rating.setScore(request.getScore());

    }
}