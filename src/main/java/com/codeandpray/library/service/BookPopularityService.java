package com.codeandpray.library.service;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.entity.BookPopularity;
import com.codeandpray.library.enums.BookPopularityPeriod;
import com.codeandpray.library.mapper.BookPopularityMapper;
import com.codeandpray.library.repo.BookPopularityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookPopularityService {

    private final BookPopularityRepo bookPopularityRepo;
    private final BookPopularityMapper bookPopularityMapper;

    @Transactional(readOnly = true)
    public List<BookPopularityResponse> findAll() {
        return bookPopularityMapper.toResponseList(bookPopularityRepo.findAll());
    }

    @Transactional
    public BookPopularityResponse save(BookPopularityRequest request) {
        Optional<BookPopularity> existingPopularity = bookPopularityRepo.findByBookIdAndPeriod(
                request.getBookId(),
                request.getPeriod()
        );

        if (existingPopularity.isPresent()) {
            BookPopularity popularity = existingPopularity.get();
            popularity.setReadCount(request.getReadCount());
            popularity.setCalculatedAt(LocalDateTime.now());
            BookPopularity savedPopularity = bookPopularityRepo.save(popularity);
            return bookPopularityMapper.toResponse(savedPopularity);
        }

        // Иначе создаём новую
        BookPopularity popularity = bookPopularityMapper.toEntity(request);
        BookPopularity savedPopularity = bookPopularityRepo.save(popularity);
        return bookPopularityMapper.toResponse(savedPopularity);
    }

    @Transactional(readOnly = true)
    public Optional<BookPopularityResponse> findById(Long id) {
        return bookPopularityRepo.findById(id)
                .map(bookPopularityMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<BookPopularityResponse> findByBookId(Long bookId) {
        return bookPopularityMapper.toResponseList(bookPopularityRepo.findByBookId(bookId));
    }

    @Transactional(readOnly = true)
    public List<BookPopularityResponse> findByPeriod(BookPopularityPeriod period) {
        return bookPopularityMapper.toResponseList(bookPopularityRepo.findByPeriod(period));
    }

    @Transactional
    public Optional<BookPopularityResponse> updateById(Long id, BookPopularityRequest updatedRequest) {
        return bookPopularityRepo.findById(id)
                .map(oldPopularity -> {
                    bookPopularityMapper.updateEntity(oldPopularity, updatedRequest);
                    BookPopularity savedPopularity = bookPopularityRepo.save(oldPopularity);
                    return bookPopularityMapper.toResponse(savedPopularity);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return bookPopularityRepo.findById(id)
                .map(popularity -> {
                    bookPopularityRepo.delete(popularity);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void incrementReadCount(Long bookId, BookPopularityPeriod period) {
        bookPopularityRepo.findByBookIdAndPeriod(bookId, period)
                .ifPresentOrElse(
                        popularity -> {
                            popularity.setReadCount(popularity.getReadCount() + 1);
                            popularity.setCalculatedAt(LocalDateTime.now());
                            bookPopularityRepo.save(popularity);
                        },
                        () -> {
                            BookPopularity newPopularity = BookPopularity.builder()
                                    .bookId(bookId)
                                    .readCount(1)
                                    .period(period)
                                    .calculatedAt(LocalDateTime.now())
                                    .build();
                            bookPopularityRepo.save(newPopularity);
                        }
                );
    }
}