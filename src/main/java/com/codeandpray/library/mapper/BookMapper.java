package com.codeandpray.library.mapper;

import com.codeandpray.library.catalog.Genre;
import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.Author;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book toEntity(CreateBookRequest dto, Set<Author> authors, Set<Genre> genres) {
        return Book.builder()
                .title(dto.getTitle())
                .authors(authors)
                .genres(genres)
                .isbn(dto.getIsbn())
                .description(dto.getDescription())
                .language(dto.getLanguage())
                .pageCount(dto.getPageCount())
                .count(dto.getCount())
                .hasAudiobook(dto.isHasAudiobook())
                .build();
    }

    public void updateEntity(Book book, UpdateBookRequest dto, Set<Author> authors, Set<Genre> genres) {
        if (dto.getTitle() != null) book.setTitle(dto.getTitle());
        if (dto.getIsbn() != null) book.setIsbn(dto.getIsbn());
        if (dto.getDescription() != null) book.setDescription(dto.getDescription());
        if (dto.getStatus() != null) book.setStatus(BookStatus.valueOf(dto.getStatus()));
        if (dto.getLanguage() != null) book.setLanguage(dto.getLanguage());
        if (dto.getPageCount() != null) book.setPageCount(dto.getPageCount());
        if (dto.getCount() != null) book.setCount(dto.getCount());
        if (authors != null) book.setAuthors(authors);
        if (genres != null) book.setGenres(genres);
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .publishDate(book.getPublishDate() != null ? book.getPublishDate().toLocalDate().toString() : null)
                .pageCount(book.getPageCount())
                .language(book.getLanguage())
                .hasAudiobook(book.isHasAudiobook())
                .readingTime(book.getReaderTime())
                .ageCategory(book.getAgeCategory() != null ? book.getAgeCategory().getValue() : null)
                .isbn(book.getIsbn())

                .genres(book.getGenres() != null
                        ? book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "))
                        : "")
                .bookAuthor(book.getAuthors() != null && !book.getAuthors().isEmpty()
                        ? book.getAuthors().stream()
                        .map(a -> a.getName() + " " + a.getLastname())
                        .collect(Collectors.joining(", "))
                        : "Автор не указан")
                .build();
    }

    public PageResponse<BookResponse> toPageResponse(Page<Book> books) {
        return PageResponse.of(books.map(this::toResponse));
    }
}