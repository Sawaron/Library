package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.BookPopularity;
import com.codeandpray.library.enums.BookPopularityPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookPopularityRepo extends JpaRepository<BookPopularity, Long> {

    List<BookPopularity> findByBook(Book book);

    Optional<BookPopularity> findByBookAndPeriod(Book book, BookPopularityPeriod period);

    Page<BookPopularity> findByBook(Book book, Pageable pageable);

    @Query("SELECT bp FROM BookPopularity bp WHERE bp.book.id = :bookId")
    List<BookPopularity> findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT bp FROM BookPopularity bp WHERE bp.book.id = :bookId")
    Page<BookPopularity> findByBookId(@Param("bookId") Long bookId, Pageable pageable);

    @Query("SELECT bp FROM BookPopularity bp WHERE bp.book.id = :bookId AND bp.period = :period")
    Optional<BookPopularity> findByBookIdAndPeriod(@Param("bookId") Long bookId,
                                                   @Param("period") BookPopularityPeriod period);


    List<BookPopularity> findByPeriod(BookPopularityPeriod period);

    Page<BookPopularity> findByPeriod(BookPopularityPeriod period, Pageable pageable);


    Page<BookPopularity> findAll(Pageable pageable);

    Page<BookPopularity> findByReadCountGreaterThanEqual(Integer minReadCount, Pageable pageable);


    @Query("SELECT bp FROM BookPopularity bp WHERE bp.period = :period AND bp.readCount >= :minReadCount")
    Page<BookPopularity> findPopularByPeriod(@Param("period") BookPopularityPeriod period,
                                             @Param("minReadCount") Integer minReadCount,
                                             Pageable pageable);

    @Query("SELECT bp FROM BookPopularity bp ORDER BY bp.readCount DESC")
    Page<BookPopularity> findMostPopular(Pageable pageable);

    // ===== Дополнительные полезные методы =====
    @Query("SELECT bp FROM BookPopularity bp WHERE bp.book.id = :bookId ORDER BY bp.period")
    List<BookPopularity> findAllPeriodsForBook(@Param("bookId") Long bookId);

    @Query("SELECT bp FROM BookPopularity bp WHERE bp.period = :period ORDER BY bp.readCount DESC")
    Page<BookPopularity> findTopByPeriod(@Param("period") BookPopularityPeriod period, Pageable pageable);
}