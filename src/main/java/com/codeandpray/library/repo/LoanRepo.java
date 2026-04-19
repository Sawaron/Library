package com.codeandpray.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codeandpray.library.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepo extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long readerId);

    Optional<Loan> findByBookIdAndStatus(Long bookId, String status);

}