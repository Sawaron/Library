package com.codeandpray.library.repo;

import com.codeandpray.library.entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {


    @Query("""
                SELECT b FROM Book b
                WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
                AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
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
