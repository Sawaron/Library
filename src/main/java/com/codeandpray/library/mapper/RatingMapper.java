package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.entity.Rating;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RatingMapper {

    public RatingResponse toResponse(Rating rating) {
        if (rating == null) {
            return null;
        }

        return RatingResponse.builder()
                .id(rating.getId())
                .bookId(rating.getBook() != null ? rating.getBook().getId() : null)
                .userId(rating.getUser() != null ? rating.getUser().getId() : null)
                .score(rating.getScore())
                .createdAt(rating.getCreatedAt())
                .build();
    }

    public Rating toEntity(RatingRequest request) {
        if (request == null) {
            return null;
        }

        return Rating.builder()
                .score(request.getScore())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void updateEntity(Rating entity, RatingRequest request) {
        if (entity == null || request == null) {
            return;
        }

        entity.setScore(request.getScore());
        entity.setCreatedAt(LocalDateTime.now());
    }
}