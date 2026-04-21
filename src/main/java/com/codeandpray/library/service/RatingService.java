package com.codeandpray.library.service;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Rating;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.mapper.RatingMapper;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.RatingRepo;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // ← По умолчанию readOnly
public class RatingService {

    private final RatingRepo ratingRepo;
    private final BookRepo bookRepo;
    private final UserRepo userRepo;
    private final RatingMapper ratingMapper;

    public List<RatingResponse> findAll() {
        return ratingRepo.findAll()
                .stream()
                .map(ratingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<RatingResponse> findByBookId(Long bookId) {
        return ratingRepo.findByBookId(bookId)
                .stream()
                .map(ratingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<RatingResponse> findByUserId(Long userId) {
        return ratingRepo.findByUserId(userId)
                .stream()
                .map(ratingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<RatingResponse> findAllPaginated(Pageable pageable) {
        return ratingRepo.findAll(pageable).map(ratingMapper::toResponse);
    }

    public Page<RatingResponse> findByBookIdPaginated(Long bookId, Pageable pageable) {
        return ratingRepo.findByBookId(bookId, pageable).map(ratingMapper::toResponse);
    }

    public Page<RatingResponse> findByUserIdPaginated(Long userId, Pageable pageable) {
        return ratingRepo.findByUserId(userId, pageable).map(ratingMapper::toResponse);
    }

    public Page<RatingResponse> findByScore(Integer score, Pageable pageable) {
        return ratingRepo.findByScore(score, pageable).map(ratingMapper::toResponse);
    }

    public Page<RatingResponse> findHighRatings(Integer minScore, Pageable pageable) {
        return ratingRepo.findByScoreGreaterThanEqual(minScore, pageable).map(ratingMapper::toResponse);
    }

    public Page<RatingResponse> findBookHighRatings(Long bookId, Integer minScore, Pageable pageable) {
        return ratingRepo.findByBookIdAndMinScore(bookId, minScore, pageable).map(ratingMapper::toResponse);
    }

    @Transactional(readOnly = false)
    public RatingResponse save(RatingRequest request) {
        Book book = bookRepo.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + request.getBookId()));

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Optional<Rating> existingRating = ratingRepo.findByBookAndUser(book, user);

        if (existingRating.isPresent()) {
            Rating rating = existingRating.get();
            rating.setScore(request.getScore());
            rating.setCreatedAt(LocalDateTime.now());
            return ratingMapper.toResponse(ratingRepo.save(rating));
        }

        Rating rating = ratingMapper.toEntity(request);
        rating.setBook(book);
        rating.setUser(user);
        return ratingMapper.toResponse(ratingRepo.save(rating));
    }

    public Optional<RatingResponse> findById(Long id) {
        return ratingRepo.findById(id).map(ratingMapper::toResponse);
    }

    @Transactional(readOnly = false)
    public Optional<RatingResponse> updateById(Long id, RatingRequest updatedRequest) {
        return ratingRepo.findById(id)
                .map(oldRating -> {
                    if (!oldRating.getBook().getId().equals(updatedRequest.getBookId())) {
                        Book newBook = bookRepo.findById(updatedRequest.getBookId())
                                .orElseThrow(() -> new RuntimeException("Book not found"));
                        oldRating.setBook(newBook);
                    }

                    if (!oldRating.getUser().getId().equals(updatedRequest.getUserId())) {
                        User newUser = userRepo.findById(updatedRequest.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        oldRating.setUser(newUser);
                    }

                    ratingMapper.updateEntity(oldRating, updatedRequest);
                    return ratingMapper.toResponse(ratingRepo.save(oldRating));
                });
    }

    @Transactional(readOnly = false)
    public boolean deleteById(Long id) {
        return ratingRepo.findById(id)
                .map(rating -> {
                    ratingRepo.delete(rating);
                    return true;
                })
                .orElse(false);
    }
}