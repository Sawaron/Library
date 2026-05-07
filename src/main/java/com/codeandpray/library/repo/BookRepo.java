package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE Book b
            SET b.count = b.count - 1
            WHERE b.id = :bookId
              AND b.count > 0
            """)
    int decrementCountIfAvailable(@Param("bookId") Long bookId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE Book b
            SET b.count = b.count + :delta,
                b.status = :status
            WHERE b.id = :bookId
            """)
    int incrementCount(
            @Param("bookId") Long bookId,
            @Param("delta") int delta,
            @Param("status") BookStatus status
    );

    @Query("""
       SELECT b FROM Book b 
       WHERE (:title IS NULL OR LOWER(CAST(b.title AS String)) LIKE LOWER(CONCAT('%', CAST(:title AS String), '%')))
         AND (:author IS NULL OR EXISTS (
                SELECT a FROM b.authors a 
                WHERE LOWER(CAST(a.name AS String)) LIKE LOWER(CONCAT('%', CAST(:author AS String), '%'))
             ))
         AND (:genre IS NULL OR EXISTS (
                SELECT g FROM b.genres g 
                WHERE LOWER(CAST(g.name AS String)) LIKE LOWER(CONCAT('%', CAST(:genre AS String), '%'))
             ))
         AND (:isbn IS NULL OR CAST(b.isbn AS String) = CAST(:isbn AS String))
         AND (:status IS NULL OR b.status = :status)
       """)
    @EntityGraph(attributePaths = {"authors", "genres"})
    Page<Book> findAllByFilters(
            @Param("title") String title,
            @Param("author") String author,
            @Param("genre") String genre,
            @Param("isbn") String isbn,
            @Param("status") BookStatus status,
            Pageable pageable
    );


}