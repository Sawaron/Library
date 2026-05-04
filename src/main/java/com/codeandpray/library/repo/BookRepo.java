package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}