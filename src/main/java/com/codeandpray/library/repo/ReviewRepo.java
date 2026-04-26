package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    Page<Review> findAllByBookId(Long bookId, Pageable pageable);

    Page<Review> findAllByUserId(Long userId, Pageable pageable);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);
}