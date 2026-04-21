package com.codeandpray.library.service;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.*;
import com.codeandpray.library.mapper.ReviewMapper;
import com.codeandpray.library.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final BookService bookService;
    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> findAll(int page, int size) {
        var pageable = PageRequest.of(page, size);
        return PageResponse.of(reviewRepo.findAll(pageable).map(reviewMapper::toResponse));
    }

    @Transactional
    public ReviewResponse create(ReviewRequest dto) {
        Book book = bookService.getById(dto.getBookId());

        Review review = Review.builder()
                .book(book)
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        return reviewMapper.toResponse(reviewRepo.save(review));
    }

    @Transactional
    public ReviewResponse update(Long id, ReviewRequest dto) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (dto.getRating() != null) review.setRating(dto.getRating());
        if (dto.getComment() != null) review.setComment(dto.getComment());

        return reviewMapper.toResponse(reviewRepo.save(review));
    }

    @Transactional(readOnly = true)
    public ReviewResponse getById(Long id) {
        return reviewRepo.findById(id)
                .map(reviewMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Transactional
    public void delete(Long id) {
        reviewRepo.deleteById(id);
    }
}