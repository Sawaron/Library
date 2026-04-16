package com.codeandpray.library.controller;

import com.codeandpray.library.dto.BookReaderResponse;
import com.codeandpray.library.dto.CreateBookRequest;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.mapper.BookMapper;
import com.codeandpray.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private void checkLibrarian(String role) {
        if (role == null || !role.equals("LIBRARIAN")) {
            throw new RuntimeException("Access denied");
        }
    }

    @GetMapping
    public Object searchBooks(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) BookStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestHeader(value = "Role", required = false) String role
    ) {

        Page<Book> books = bookService.getBooks(
                title, author, genre, isbn, status, page, size
        );

        if (id != null) {
            checkLibrarian(role);
            Book book = bookService.getById(id);
            return book;
        }

        if ("LIBRARIAN".equals(role)) {
            return books;
        }

        return books.map(BookMapper::toReader);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id,
                        @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return bookService.getById(id);
    }

    @PostMapping
    public Book create(@RequestBody CreateBookRequest request,
                       @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return bookService.create(request);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id,
                       @RequestBody Book book,
                       @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return bookService.updateById(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        bookService.deleteById(id);
    }
}