package com.codeandpray.library.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codeandpray.library.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepo extends JpaRepository<Loan, Long> {

    Page<Loan> findByUserId(Long readerId, Pageable pageable);

    Optional<Loan> findByBookIdAndStatus(Long bookId, String status);

}