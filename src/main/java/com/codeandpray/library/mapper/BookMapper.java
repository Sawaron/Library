package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.*;
import com.codeandpray.library.enums.BookStatus;

public class BookMapper {


    public static Book toEntity(CreateBookRequest dto) {
        return Book.builder()
                .title(dto.getTitle())
                .bookGenre(dto.getGenre())
                .isbn(dto.getIsbn())
                .description(dto.getSummary())
                .build();
    }

    public static void updateEntity(Book book, UpdateBookRequest dto) {

        if (dto.getTitle() != null) book.setTitle(dto.getTitle());
        if (dto.getGenre() != null) book.setBookGenre(dto.getGenre());
        if (dto.getIsbn() != null) book.setIsbn(dto.getIsbn());
        if (dto.getSummary() != null) book.setDescription(dto.getSummary());

        if (dto.getStatus() != null) {
            book.setStatus(BookStatus.valueOf(dto.getStatus()));
        }
    }

    public static BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .publishDate(
                        book.getPublishDate() != null
                                ? book.getPublishDate().toLocalDate().toString()
                                : null
                )
                .pageCount(book.getPageCount())
                .language(book.getLanguage())
                .price(book.getPrice())
                .hasAudiobook(book.isHasAudiobook())
                .readingTime(book.getReaderTime())
                .ageCategory(
                        book.getAgeCategory() != null
                                ? book.getAgeCategory().getValue()
                                : null
                )
                .isbn(book.getIsbn())
                .bookGenre(book.getBookGenre())
                .bookAuthor(
                        book.getBookAuthor() != null
                                ? book.getBookAuthor().getName()
                                : null
                )
                .build();
    }
}