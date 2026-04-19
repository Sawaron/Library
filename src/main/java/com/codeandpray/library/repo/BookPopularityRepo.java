package com.codeandpray.library.repo;

import com.codeandpray.library.entity.BookPopularity;
import com.codeandpray.library.enums.BookPopularityPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookPopularityRepo extends JpaRepository<BookPopularity, Long> {

    List<BookPopularity> findByBookId(Long bookId);

    List<BookPopularity> findByPeriod(BookPopularityPeriod period);

    Optional<BookPopularity> findByBookIdAndPeriod(Long bookId, BookPopularityPeriod period);
}