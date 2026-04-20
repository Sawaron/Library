package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    List<Rating> findByBookId(Long bookId);
    List<Rating> findByUserId(Long userId);
    Optional<Rating> findByBookIdAndUserId(Long bookId, Long userId);

    Page<Rating> findAll(Pageable pageable);

    Page<Rating> findByBookId(Long bookId, Pageable pageable);

    Page<Rating> findByUserId(Long userId, Pageable pageable);

    Page<Rating> findByScore(Integer score, Pageable pageable);

    Page<Rating> findByScoreGreaterThanEqual(Integer minScore, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.bookId = :bookId AND r.score >= :minScore")
    Page<Rating> findByBookIdAndMinScore(@Param("bookId") Long bookId,
                                         @Param("minScore") Integer minScore,
                                         Pageable pageable);
}