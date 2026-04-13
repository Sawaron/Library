package com.codeandpray.library.loan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.codeandpray.library.loan.dto.*;
import com.codeandpray.library.loan.entity.Loan;
import com.codeandpray.library.loan.repository.LoanRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanResponse createLoan(LoanRequest request) {

        loanRepository.findByBookIdAndStatus(request.getBookId(), "ACTIVE")
                .ifPresent(l -> {
                    throw new RuntimeException("Book already loaned");
                });

        Loan loan = Loan.builder()
                .bookId(request.getBookId())
                .bookTitle(request.getBookTitle())
                .readerId(request.getReaderId())
                .readerName(request.getReaderName())
                .loanDate(LocalDate.now())
                .returnDate(request.getReturnDate())
                .status("ACTIVE")
                .build();

        return mapToResponse(loanRepository.save(loan));
    }

    public List<LoanResponse> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LoanResponse getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public LoanResponse returnBook(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setActualReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");

        return mapToResponse(loanRepository.save(loan));
    }

    public List<LoanResponse> getLoansByReader(Long readerId) {
        return loanRepository.findByReaderId(readerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LoanResponse mapToResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBookId())
                .bookTitle(loan.getBookTitle())
                .readerId(loan.getReaderId())
                .readerName(loan.getReaderName())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .actualReturnDate(loan.getActualReturnDate())
                .status(loan.getStatus())
                .build();
    }
}