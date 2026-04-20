package com.codeandpray.library.repo;

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

    List<BookPopularity> findByBookId(Long bookId);
    List<BookPopularity> findByPeriod(BookPopularityPeriod period);
    Optional<BookPopularity> findByBookIdAndPeriod(Long bookId, BookPopularityPeriod period);

    Page<BookPopularity> findAll(Pageable pageable);

    Page<BookPopularity> findByBookId(Long bookId, Pageable pageable);

    Page<BookPopularity> findByPeriod(BookPopularityPeriod period, Pageable pageable);

    Page<BookPopularity> findByReadCountGreaterThanEqual(Integer minReadCount, Pageable pageable);

    @Query("SELECT bp FROM BookPopularity bp WHERE bp.period = :period AND bp.readCount >= :minReadCount")
    Page<BookPopularity> findPopularByPeriod(@Param("period") BookPopularityPeriod period,
                                             @Param("minReadCount") Integer minReadCount,
                                             Pageable pageable);

    @Query("SELECT bp FROM BookPopularity bp ORDER BY bp.readCount DESC")
    Page<BookPopularity> findMostPopular(Pageable pageable);
}