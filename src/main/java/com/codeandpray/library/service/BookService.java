package com.codeandpray.library.service;

import com.codeandpray.library.catalog.Genre;
import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.*;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.mapper.BookMapper;
import com.codeandpray.library.repo.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {

    private final BookRepo bookRepository;
    private final AuthorRepo authorRepo;
    private final GenreRepo genreRepo;
    private final BookMapper bookMapper;

    public BookService(BookRepo bookRepository, AuthorRepo authorRepo,
                       GenreRepo genreRepo, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
        this.bookMapper = bookMapper;
    }


    @Transactional(readOnly = true)
    public Page<Book> getBooks(String title, String author, String genre,
                               String isbn, BookStatus status,
                               int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAllByFilters(title, author, genre, isbn, status, pageable);
    }

    @Transactional(readOnly = true)
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Transactional
    public Book create(CreateBookRequest dto) {
        Set<Author> authors = new HashSet<>(authorRepo.findAllById(dto.getAuthorIds()));
        Set<Genre> genres = new HashSet<>(genreRepo.findAllById(dto.getGenreIds()));

        Book book = bookMapper.toEntity(dto, authors, genres);
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateById(Long id, UpdateBookRequest dto) {
        Book book = getById(id);

        Set<Author> authors = (dto.getAuthorIds() != null)
                ? new HashSet<>(authorRepo.findAllById(dto.getAuthorIds()))
                : book.getAuthors();

        Set<Genre> genres = (dto.getGenreIds() != null)
                ? new HashSet<>(genreRepo.findAllById(dto.getGenreIds()))
                : book.getGenres();

        bookMapper.updateEntity(book, dto, authors, genres);
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.delete(getById(id));
    }
}