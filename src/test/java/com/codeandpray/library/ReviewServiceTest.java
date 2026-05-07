package com.codeandpray.library;

import com.codeandpray.library.dto.ReviewRequest;
import com.codeandpray.library.dto.ReviewResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Review;
import com.codeandpray.library.exception.logic.ReviewAlreadyExists;
import com.codeandpray.library.mapper.ReviewMapper;
import com.codeandpray.library.repo.ReviewRepo;
import com.codeandpray.library.service.BookService;
import com.codeandpray.library.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock private ReviewRepo reviewRepo;
    @Mock private BookService bookService;
    @Mock private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    // TC-02: Дубликат отзыва → 409
    @Test
    @DisplayName("TC-02: Повторный отзыв на ту же книгу → ReviewAlreadyExists (409)")
    void createReview_duplicate_shouldThrow409() {
        ReviewRequest request = new ReviewRequest();
        request.setBookId(42L);
        request.setUserId(1001L);
        request.setRating(5);
        request.setComment("Отличная книга, очень понравилась!");

        when(reviewRepo.existsByBookIdAndUserId(42L, 1001L)).thenReturn(true);

        assertThatThrownBy(() -> reviewService.create(request))
                .isInstanceOf(ReviewAlreadyExists.class);

        verify(reviewRepo, never()).save(any());
    }

    // TC-02b: Первый отзыв создаётся успешно
    @Test
    @DisplayName("TC-02b: Первый отзыв на книгу создаётся без ошибок")
    void createReview_firstTime_shouldSucceed() {
        ReviewRequest request = new ReviewRequest();
        request.setBookId(42L);
        request.setUserId(1001L);
        request.setRating(5);
        request.setComment("Отличная книга, очень понравилась!");

        Book book = Book.builder().id(42L).title("Преступление и наказание").build();
        Review savedReview = Review.builder()
                .id(1L).book(book).userId(1001L)
                .rating(5).comment("Отличная книга, очень понравилась!").build();
        ReviewResponse expectedResponse = ReviewResponse.builder()
                .id(1L).bookId(42L).userId(1001L).rating(5).build();

        when(reviewRepo.existsByBookIdAndUserId(42L, 1001L)).thenReturn(false);
        when(bookService.getById(42L)).thenReturn(book);
        when(reviewRepo.save(any())).thenReturn(savedReview);
        when(reviewMapper.toResponse(any())).thenReturn(expectedResponse);

        ReviewResponse result = reviewService.create(request);

        verify(reviewRepo, times(1)).save(any());
    }
}
