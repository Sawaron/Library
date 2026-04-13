package com.codeandpray.library.controller;

import com.codeandpray.library.entity.*;
import com.codeandpray.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
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
    public Page<Book> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) BookStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return bookService.getBooks(title, author, genre, isbn, status, page, size);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search")
    public Book getByTitle(@RequestParam String title) {
        return bookService.getByTitle(title);
    }

    @PostMapping
    public Book create(@RequestBody Book book,
                       @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public Book updateById(@PathVariable Long id,
                           @RequestBody Book book,
                           @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return bookService.updateById(id, book);
    }

    @PutMapping("/by-title")
    public Book updateByTitle(@RequestParam String title,
                              @RequestBody Book book,
                              @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return bookService.updateByTitle(title, book);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id,
                           @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        bookService.deleteById(id);
    }

    @DeleteMapping("/by-title")
    public void deleteByTitle(@RequestParam String title,
                              @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        bookService.deleteByTitle(title);
    }
}


