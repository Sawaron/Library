package com.codeandpray.library.service;

import com.codeandpray.library.dto.CreateBookRequest;
import com.codeandpray.library.entity.Author;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.mapper.BookMapper;
import com.codeandpray.library.repo.AuthorRepo;
import com.codeandpray.library.repo.BookRepo;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepo bookRepository;
    private final AuthorRepo authorRepo;

    public BookService(BookRepo bookRepository, AuthorRepo authorRepo) {
        this.bookRepository = bookRepository;
        this.authorRepo = authorRepo;
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


        Author author = authorRepo.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + dto.getAuthorId()));


        Book book = BookMapper.toEntity(dto);


        book.setBookAuthor(author);
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

        if (updatedBook.getBookAuthor() != null)
            book.setBookAuthor(updatedBook.getBookAuthor());

        if (updatedBook.getBookGenre() != null)
            book.setBookGenre(updatedBook.getBookGenre());

        if (updatedBook.getIsbn() != null)
            book.setIsbn(updatedBook.getIsbn());

        if (updatedBook.getDescription() != null)
            book.setDescription(updatedBook.getDescription());

        if (updatedBook.getStatus() != null)
            book.setStatus(updatedBook.getStatus());
    }

    public void deleteById(Long id) {
        bookRepository.delete(getById(id));
    }
}