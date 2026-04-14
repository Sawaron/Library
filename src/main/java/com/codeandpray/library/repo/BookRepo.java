package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query("""
        SELECT b FROM Book b
        WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
          AND (:author IS NULL OR LOWER(b.author.name) LIKE LOWER(CONCAT('%', :author, '%')))
          AND (:genre IS NULL OR b.genre = :genre)
          AND (:isbn IS NULL OR b.isbn = :isbn)
          AND (:status IS NULL OR b.status = :status)
    """)
    Page<Book> findAllByFilters(
            @Param("title") String title,
            @Param("author") String author,
            @Param("genre") String genre,
            @Param("isbn") String isbn,
            @Param("status") BookStatus status,
            Pageable pageable
    );

    Optional<Book> findByTitle(String title);
}