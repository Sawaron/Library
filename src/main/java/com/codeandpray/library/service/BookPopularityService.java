package com.codeandpray.library.service;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.entity.BookPopularity;
import com.codeandpray.library.enums.BookPopularityPeriod;
import com.codeandpray.library.repo.BookPopularityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookPopularityService {

    private final BookPopularityRepo bookPopularityRepo;

    private BookPopularityResponse mapToResponse(BookPopularity popularity) {
        return BookPopularityResponse.builder()
                .id(popularity.getId())
                .bookId(popularity.getBookId())
                .readCount(popularity.getReadCount())
                .period(popularity.getPeriod())
                .calculatedAt(popularity.getCalculatedAt())
                .build();
    }

    private BookPopularity mapToEntity(BookPopularityRequest request) {
        return BookPopularity.builder()
                .bookId(request.getBookId())
                .readCount(request.getReadCount())
                .period(request.getPeriod())
                .calculatedAt(LocalDateTime.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<BookPopularityResponse> findAll() {
        return bookPopularityRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookPopularityResponse save(BookPopularityRequest request) {
        // Проверяем, есть ли уже запись для этой книги и периода
        Optional<BookPopularity> existingPopularity = bookPopularityRepo.findByBookIdAndPeriod(
                request.getBookId(),
                request.getPeriod()
        );

        if (existingPopularity.isPresent()) {
            // Если запись есть - обновляем счётчик и дату
            BookPopularity popularity = existingPopularity.get();
            popularity.setReadCount(request.getReadCount());
            popularity.setCalculatedAt(LocalDateTime.now());
            BookPopularity savedPopularity = bookPopularityRepo.save(popularity);
            return mapToResponse(savedPopularity);
        }

        // Иначе создаём новую
        BookPopularity popularity = mapToEntity(request);
        BookPopularity savedPopularity = bookPopularityRepo.save(popularity);
        return mapToResponse(savedPopularity);
    }

    @Transactional(readOnly = true)
    public Optional<BookPopularityResponse> findById(Long id) {
        return bookPopularityRepo.findById(id)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public List<BookPopularityResponse> findByBookId(Long bookId) {
        return bookPopularityRepo.findByBookId(bookId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookPopularityResponse> findByPeriod(BookPopularityPeriod period) {
        return bookPopularityRepo.findByPeriod(period)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<BookPopularityResponse> updateById(Long id, BookPopularityRequest updatedRequest) {
        return bookPopularityRepo.findById(id)
                .map(oldPopularity -> {
                    oldPopularity.setBookId(updatedRequest.getBookId());
                    oldPopularity.setReadCount(updatedRequest.getReadCount());
                    oldPopularity.setPeriod(updatedRequest.getPeriod());
                    oldPopularity.setCalculatedAt(LocalDateTime.now());

                    BookPopularity savedPopularity = bookPopularityRepo.save(oldPopularity);
                    return mapToResponse(savedPopularity);
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