package com.codeandpray.library.service;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.repo.BookRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepo bookRepository;

    public BookService(BookRepo bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getBooks(String title, String author, String genre,
                               String isbn, BookStatus status,
                               int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.search(title, author, genre, isbn, status, pageable);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book create(Book book) {

        if (book.getTitle() == null ||
                book.getAuthor() == null ||
                book.getIsbn() == null) {
            throw new RuntimeException("Invalid data");
        }

        book.setStatus(BookStatus.AVAILABLE);

        return bookRepository.save(book);
    }

    public Book updateById(Long id, Book updatedBook) {

        Book book = getById(id);

        applyUpdates(book, updatedBook);

        return bookRepository.save(book);
    }

    private void applyUpdates(Book book, Book updatedBook) {

        if (updatedBook.getTitle() != null)
            book.setTitle(updatedBook.getTitle());

        if (updatedBook.getAuthor() != null)
            book.setAuthor(updatedBook.getAuthor());

        if (updatedBook.getGenre() != null)
            book.setGenre(updatedBook.getGenre());

        if (updatedBook.getIsbn() != null)
            book.setIsbn(updatedBook.getIsbn());

        if (updatedBook.getSummary() != null)
            book.setSummary(updatedBook.getSummary());

        if (updatedBook.getStatus() != null)
            book.setStatus(updatedBook.getStatus());
    }

    public void deleteById(Long id) {
        bookRepository.delete(getById(id));
    }
}