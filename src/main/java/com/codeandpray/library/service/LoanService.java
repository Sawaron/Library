package com.codeandpray.library.service;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.dto.LoanResponse;
import com.codeandpray.library.dto.PageResponse; // Наша обертка
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.entity.Loan;
import com.codeandpray.library.mapper.LoanMapper;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.UserRepo;
import com.codeandpray.library.repo.LoanRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepo loanRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final LoanMapper loanMapper;

    @Transactional
    public LoanResponse createLoan(LoanRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getCount() <= 0) {
            throw new RuntimeException("No copies available in library");
        }

        User user = userRepository.findById(request.getReaderId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Loan loan = Loan.builder()
                .book(book)
                .user(user)
                .loanDate(LocalDate.now())
                .returnDate(request.getReturnDate())
                .status("ACTIVE")
                .build();

        book.setCount(book.getCount() - 1);
        bookRepository.save(book);

        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public PageResponse<LoanResponse> getAllLoans(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Loan> loanPage = loanRepository.findAll(pageable);

        return PageResponse.of(loanPage.map(loanMapper::toResponse));
    }

    @Transactional(readOnly = true)
    public LoanResponse getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(loanMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Transactional
    public LoanResponse returnBook(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setActualReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");

        Book book = loan.getBook();
        book.setCount(book.getCount() + 1);
        bookRepository.save(book);

        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public PageResponse<LoanResponse> getLoansByReader(Long readerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Передаем пагинацию в репозиторий
        Page<Loan> loanPage = loanRepository.findByUserId(readerId, pageable);

        return PageResponse.of(loanPage.map(loanMapper::toResponse));
    }

    @Transactional
    public LoanResponse updateLoan(Long id, LoanRequest request) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (request.getReturnDate() != null) {
            loan.setReturnDate(request.getReturnDate());
        }

        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional
    public void cancelLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if ("CANCELLED".equals(loan.getStatus())) {
            throw new RuntimeException("Loan is already cancelled");
        }

        loan.setStatus("CANCELLED");

        Book book = loan.getBook();
        book.setCount(book.getCount() + 1);
        bookRepository.save(book);

        loanRepository.save(loan);
    }

}