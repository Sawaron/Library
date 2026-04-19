package com.codeandpray.library.service;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.entity.Rating;
import com.codeandpray.library.repo.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepo ratingRepo;

    private RatingResponse mapToResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .bookId(rating.getBookId())
                .userId(rating.getUserId())
                .score(rating.getScore())
                .createdAt(rating.getCreatedAt())
                .build();
    }

    private Rating mapToEntity(RatingRequest request) {
        return Rating.builder()
                .bookId(request.getBookId())
                .userId(request.getUserId())
                .score(request.getScore())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> findAll() {
        return ratingRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RatingResponse save(RatingRequest request) {
        // Проверяем, не ставил ли уже пользователь оценку этой книге
        Optional<Rating> existingRating = ratingRepo.findByBookIdAndUserId(
                request.getBookId(),
                request.getUserId()
        );

        if (existingRating.isPresent()) {
            // Если оценка уже есть - обновляем её
            Rating rating = existingRating.get();
            rating.setScore(request.getScore());
            rating.setCreatedAt(LocalDateTime.now());
            Rating savedRating = ratingRepo.save(rating);
            return mapToResponse(savedRating);
        }

        // Иначе создаём новую
        Rating rating = mapToEntity(request);
        Rating savedRating = ratingRepo.save(rating);
        return mapToResponse(savedRating);
    }

    @Transactional(readOnly = true)
    public Optional<RatingResponse> findById(Long id) {
        return ratingRepo.findById(id)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> findByBookId(Long bookId) {
        return ratingRepo.findByBookId(bookId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> findByUserId(Long userId) {
        return ratingRepo.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<RatingResponse> updateById(Long id, RatingRequest updatedRequest) {
        return ratingRepo.findById(id)
                .map(oldRating -> {
                    oldRating.setBookId(updatedRequest.getBookId());
                    oldRating.setUserId(updatedRequest.getUserId());
                    oldRating.setScore(updatedRequest.getScore());
                    oldRating.setCreatedAt(LocalDateTime.now());

                    Rating savedRating = ratingRepo.save(oldRating);
                    return mapToResponse(savedRating);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return ratingRepo.findById(id)
                .map(rating -> {
                    ratingRepo.delete(rating);
                    return true;
                })
                .orElse(false);
    }
}