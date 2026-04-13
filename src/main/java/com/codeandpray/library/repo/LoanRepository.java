package com.codeandpray.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codeandpray.library.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByReaderId(Long readerId);

    Optional<Loan> findByBookIdAndStatus(Long bookId, String status);
}