package com.codeandpray.library.service;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.entity.Rating;
import com.codeandpray.library.mapper.RatingMapper;
import com.codeandpray.library.repo.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepo ratingRepo;
    private final RatingMapper ratingMapper;

    @Transactional(readOnly = true)
    public List<RatingResponse> findAll() {
        return ratingMapper.toResponseList(ratingRepo.findAll());
    }

    @Transactional
    public RatingResponse save(RatingRequest request) {
        Optional<Rating> existingRating = ratingRepo.findByBookIdAndUserId(
                request.getBookId(),
                request.getUserId()
        );

        if (existingRating.isPresent()) {
            Rating rating = existingRating.get();
            rating.setScore(request.getScore());
            rating.setCreatedAt(LocalDateTime.now());
            Rating savedRating = ratingRepo.save(rating);
            return ratingMapper.toResponse(savedRating);
        }

        Rating rating = ratingMapper.toEntity(request);
        Rating savedRating = ratingRepo.save(rating);
        return ratingMapper.toResponse(savedRating);
    }

    @Transactional(readOnly = true)
    public Optional<RatingResponse> findById(Long id) {
        return ratingRepo.findById(id)
                .map(ratingMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> findByBookId(Long bookId) {
        return ratingMapper.toResponseList(ratingRepo.findByBookId(bookId));
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> findByUserId(Long userId) {
        return ratingMapper.toResponseList(ratingRepo.findByUserId(userId));
    }

    @Transactional
    public Optional<RatingResponse> updateById(Long id, RatingRequest updatedRequest) {
        return ratingRepo.findById(id)
                .map(oldRating -> {
                    ratingMapper.updateEntity(oldRating, updatedRequest);
                    Rating savedRating = ratingRepo.save(oldRating);
                    return ratingMapper.toResponse(savedRating);
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