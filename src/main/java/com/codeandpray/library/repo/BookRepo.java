    package com.codeandpray.library.repo;

    import com.codeandpray.library.entity.Book;
    import com.codeandpray.library.enums.BookStatus;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    @Repository
    public interface BookRepo extends JpaRepository<Book, Long> {

        @Query("""
    SELECT DISTINCT b FROM Book b
    LEFT JOIN b.authors a
    LEFT JOIN b.genres g
    WHERE (:title IS NULL OR LOWER(CAST(b.title AS string)) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%')))
      AND (:author IS NULL OR LOWER(CAST(a.name AS string)) LIKE LOWER(CONCAT('%', CAST(:author AS string), '%')))
      AND (:genre IS NULL OR LOWER(CAST(g.name AS string)) LIKE LOWER(CONCAT('%', CAST(:genre AS string), '%')))
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
    }