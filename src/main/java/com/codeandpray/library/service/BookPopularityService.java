package com.codeandpray.library.service;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.BookPopularity;
import com.codeandpray.library.enums.BookPopularityPeriod;
import com.codeandpray.library.exception.entity.BookNotFoundException;
import com.codeandpray.library.mapper.BookPopularityMapper;
import com.codeandpray.library.repo.BookPopularityRepo;
import com.codeandpray.library.repo.BookRepo;
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
public class BookPopularityService {

    private final BookPopularityRepo bookPopularityRepo;
    private final BookRepo bookRepo;
    private final BookPopularityMapper bookPopularityMapper;

    public List<BookPopularityResponse> findAll() {
        return bookPopularityRepo.findAll()
                .stream()
                .map(bookPopularityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookPopularityResponse> findByBookId(Long bookId) {
        return bookPopularityRepo.findByBookId(bookId)
                .stream()
                .map(bookPopularityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookPopularityResponse> findByPeriod(BookPopularityPeriod period) {
        return bookPopularityRepo.findByPeriod(period)
                .stream()
                .map(bookPopularityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<BookPopularityResponse> findAllPaginated(Pageable pageable) {
        return bookPopularityRepo.findAll(pageable).map(bookPopularityMapper::toResponse);
    }

    public Page<BookPopularityResponse> findByBookIdPaginated(Long bookId, Pageable pageable) {
        return bookPopularityRepo.findByBookId(bookId, pageable).map(bookPopularityMapper::toResponse);
    }

    public Page<BookPopularityResponse> findByPeriodPaginated(BookPopularityPeriod period, Pageable pageable) {
        return bookPopularityRepo.findByPeriod(period, pageable).map(bookPopularityMapper::toResponse);
    }

    public Page<BookPopularityResponse> findPopularBooks(Integer minReadCount, Pageable pageable) {
        return bookPopularityRepo.findByReadCountGreaterThanEqual(minReadCount, pageable)
                .map(bookPopularityMapper::toResponse);
    }

    public Page<BookPopularityResponse> findPopularByPeriod(BookPopularityPeriod period,
                                                            Integer minReadCount,
                                                            Pageable pageable) {
        return bookPopularityRepo.findPopularByPeriod(period, minReadCount, pageable)
                .map(bookPopularityMapper::toResponse);
    }

    public Page<BookPopularityResponse> findMostPopular(Pageable pageable) {
        return bookPopularityRepo.findMostPopular(pageable).map(bookPopularityMapper::toResponse);
    }

    @Transactional(readOnly = false)
    public BookPopularityResponse save(BookPopularityRequest request) {
        Book book = bookRepo.findById(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Книга с ID: " + request.getBookId() + " не найдена"));

        Optional<BookPopularity> existingPopularity = bookPopularityRepo.findByBookIdAndPeriod(
                request.getBookId(),
                request.getPeriod()
        );

        if (existingPopularity.isPresent()) {
            BookPopularity popularity = existingPopularity.get();
            popularity.setReadCount(request.getReadCount());
            popularity.setCalculatedAt(LocalDateTime.now());
            return bookPopularityMapper.toResponse(bookPopularityRepo.save(popularity));
        }

        BookPopularity popularity = bookPopularityMapper.toEntity(request);
        popularity.setBook(book);
        return bookPopularityMapper.toResponse(bookPopularityRepo.save(popularity));
    }

    public Optional<BookPopularityResponse> findById(Long id) {
        return bookPopularityRepo.findById(id).map(bookPopularityMapper::toResponse);
    }

    @Transactional(readOnly = false)
    public Optional<BookPopularityResponse> updateById(Long id, BookPopularityRequest updatedRequest) {
        return bookPopularityRepo.findById(id)
                .map(oldPopularity -> {
                    if (!oldPopularity.getBook().getId().equals(updatedRequest.getBookId())) {
                        Book newBook = bookRepo.findById(updatedRequest.getBookId())
                                .orElseThrow(() -> new BookNotFoundException("Книга с ID: " + updatedRequest.getBookId() + " не найдена"));
                        oldPopularity.setBook(newBook);
                    }

                    bookPopularityMapper.updateEntity(oldPopularity, updatedRequest);
                    return bookPopularityMapper.toResponse(bookPopularityRepo.save(oldPopularity));
                });
    }

    @Transactional(readOnly = false)
    public boolean deleteById(Long id) {
        return bookPopularityRepo.findById(id)
                .map(popularity -> {
                    bookPopularityRepo.delete(popularity);
                    return true;
                })
                .orElseThrow(() -> new BookNotFoundException("Запись о популярности с ID: " + id + " не найдена"));
    }

    @Transactional(readOnly = false)
    public void incrementReadCount(Long bookId, BookPopularityPeriod period) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID: " + bookId + " не найдена"));

        bookPopularityRepo.findByBookIdAndPeriod(bookId, period)
                .ifPresentOrElse(
                        popularity -> {
                            popularity.setReadCount(popularity.getReadCount() + 1);
                            popularity.setCalculatedAt(LocalDateTime.now());
                            bookPopularityRepo.save(popularity);
                        },
                        () -> {
                            BookPopularity newPopularity = BookPopularity.builder()
                                    .book(book)
                                    .readCount(1)
                                    .period(period)
                                    .calculatedAt(LocalDateTime.now())
                                    .build();
                            bookPopularityRepo.save(newPopularity);
                        }
                );
    }
}