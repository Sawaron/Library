package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    List<Rating> findByBookId(Long bookId);

    List<Rating> findByUserId(Long userId);

    Optional<Rating> findByBookIdAndUserId(Long bookId, Long userId);
}