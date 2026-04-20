package com.codeandpray.library.service;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.*;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.mapper.BookMapper;
import com.codeandpray.library.repo.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {

    private final BookRepo bookRepository;
    private final AuthorRepo authorRepo;
    private final GenreRepo genreRepo;

    public BookService(BookRepo bookRepository,
                       AuthorRepo authorRepo,
                       GenreRepo genreRepo) {
        this.bookRepository = bookRepository;
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
    }

    public Page<Book> getBooks(String title, String author, String genre,
                               String isbn, BookStatus status,
                               int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAllByFilters(title, author, genre, isbn, status, pageable);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book create(CreateBookRequest dto) {

        Set<Author> authors = new HashSet<>(authorRepo.findAllById(dto.getAuthorIds()));
        Set<Genre> genres = new HashSet<>(genreRepo.findAllById(dto.getGenreIds()));

        Book book = BookMapper.toEntity(dto, authors, genres);
        book.setStatus(BookStatus.AVAILABLE);

        return bookRepository.save(book);
    }

    public Book updateById(Long id, UpdateBookRequest dto) {

        Book book = getById(id);

        Set<Author> authors = dto.getAuthorIds() != null
                ? new HashSet<>(authorRepo.findAllById(dto.getAuthorIds()))
                : null;

        Set<Genre> genres = dto.getGenreIds() != null
                ? new HashSet<>(genreRepo.findAllById(dto.getGenreIds()))
                : null;

        BookMapper.updateEntity(book, dto, authors, genres);

        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.delete(getById(id));
    }
}