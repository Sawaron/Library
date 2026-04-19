package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;

public class BookMapper {


    public static Book toEntity(CreateBookRequest dto) {
        return Book.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .isbn(dto.getIsbn())
                .summary(dto.getSummary())
                .build();
    }

    public static void updateEntity(Book book, UpdateBookRequest dto) {

        if (dto.getTitle() != null) book.setTitle(dto.getTitle());
        if (dto.getGenre() != null) book.setGenre(dto.getGenre());
        if (dto.getIsbn() != null) book.setIsbn(dto.getIsbn());
        if (dto.getSummary() != null) book.setSummary(dto.getSummary());

        if (dto.getStatus() != null) {
            book.setStatus(BookStatus.valueOf(dto.getStatus()));
        }
    }

    public static BookReaderResponse toReader(Book book) {
        return BookReaderResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor().getName())
                .genre(book.getGenre())
                .summary(book.getSummary())
                .status(book.getStatus().name())
                .build();
    }
}