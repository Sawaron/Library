package com.codeandpray.library.repo;

import com.codeandpray.library.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codeandpray.library.entity.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {

        Page<Loan> findByUserId(Long userId, Pageable pageable);

        Optional<Loan> findByBookIdAndStatus(Long bookId, LoanStatus status);
    }
