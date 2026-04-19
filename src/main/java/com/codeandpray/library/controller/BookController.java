package com.codeandpray.library.controller;

import com.codeandpray.library.dto.BookResponse;
import com.codeandpray.library.dto.CreateBookRequest;
import com.codeandpray.library.dto.UpdateBookRequest;
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

    // 🔐 проверка роли
    private void checkLibrarian(String role) {
        if (role == null || !role.equals("LIBRARIAN")) {
            throw new RuntimeException("Access denied");
        }
    }

    @GetMapping
    public Page<BookResponse> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) BookStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Page<Book> books = bookService.getBooks(
                title, author, genre, isbn, status, page, size
        );

        return books.map(BookMapper::toResponse);
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id,
                                @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return BookMapper.toResponse(bookService.getById(id));
    }

    @PostMapping
    public BookResponse create(@RequestBody CreateBookRequest request,
                               @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return BookMapper.toResponse(bookService.create(request));
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id,
                               @RequestBody UpdateBookRequest request,
                               @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        return BookMapper.toResponse(bookService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader(value = "Role", required = false) String role) {

        checkLibrarian(role);
        bookService.deleteById(id);
    }
}