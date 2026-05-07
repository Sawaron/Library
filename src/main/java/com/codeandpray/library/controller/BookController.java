package com.codeandpray.library.controller;

import com.codeandpray.library.dto.CreateBookRequest;
import com.codeandpray.library.dto.PageResponse;
import com.codeandpray.library.dto.UpdateBookRequest;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.mapper.BookMapper;
import com.codeandpray.library.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.codeandpray.library.dto.BookResponse;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public PageResponse<BookResponse> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) BookStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return bookMapper.toPageResponse(bookService.getBooks(title, author, genre, isbn, status, page, size));
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN', 'READER')")
    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id) {
        return bookMapper.toResponse(bookService.getById(id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public BookResponse create(@RequestBody CreateBookRequest request) {
        return bookMapper.toResponse(bookService.create(request));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @RequestBody UpdateBookRequest request) {
        return bookMapper.toResponse(bookService.updateById(id, request));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}