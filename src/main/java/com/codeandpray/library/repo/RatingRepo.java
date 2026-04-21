package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Rating;
import com.codeandpray.library.entity.User;
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

    List<Rating> findByBook(Book book);

    List<Rating> findByUser(User user);

    Optional<Rating> findByBookAndUser(Book book, User user);

    Page<Rating> findAll(Pageable pageable);

    Page<Rating> findByBook(Book book, Pageable pageable);

    Page<Rating> findByUser(User user, Pageable pageable);

    Page<Rating> findByScore(Integer score, Pageable pageable);

    Page<Rating> findByScoreGreaterThanEqual(Integer minScore, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.book.id = :bookId AND r.score >= :minScore")
    Page<Rating> findByBookIdAndMinScore(@Param("bookId") Long bookId,
                                         @Param("minScore") Integer minScore,
                                         Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.book.id = :bookId")
    List<Rating> findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT r FROM Rating r WHERE r.book.id = :bookId")
    Page<Rating> findByBookId(@Param("bookId") Long bookId, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId")
    List<Rating> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId")
    Page<Rating> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.book.id = :bookId AND r.user.id = :userId")
    Optional<Rating> findByBookIdAndUserId(@Param("bookId") Long bookId,
                                           @Param("userId") Long userId);
}