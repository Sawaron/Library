package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.entity.Rating;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RatingMapper {

    public RatingResponse toResponse(Rating rating) {
        if (rating == null) {
            return null;
        }

        return RatingResponse.builder()
                .id(rating.getId())
                .bookId(rating.getBookId())
                .userId(rating.getUserId())
                .score(rating.getScore())
                .createdAt(rating.getCreatedAt())
                .build();
    }

    public Rating toEntity(RatingRequest request) {
        if (request == null) {
            return null;
        }

        return Rating.builder()
                .bookId(request.getBookId())
                .userId(request.getUserId())
                .score(request.getScore())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<RatingResponse> toResponseList(List<Rating> ratings) {
        if (ratings == null) {
            return List.of();
        }

        return ratings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(Rating rating, RatingRequest request) {
        if (rating == null || request == null) {
            return;
        }

        rating.setBookId(request.getBookId());
        rating.setUserId(request.getUserId());
        rating.setScore(request.getScore());
        rating.setCreatedAt(LocalDateTime.now());
    }
}