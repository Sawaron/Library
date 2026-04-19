package com.codeandpray.library.service;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.dto.LoanResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.ReservationRepo;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.codeandpray.library.entity.Loan;
import com.codeandpray.library.repo.LoanRepo;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepo loanRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final ReservationRepo reservationRepository;

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
        return loanRepository.findByUserId(readerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LoanResponse mapToResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBook().getId())
                .bookTitle(loan.getBook().getTitle())
                .readerId(loan.getUser().getId())
                .readerName(loan.getUser().getFirstname() + " " + loan.getUser().getLastname())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .actualReturnDate(loan.getActualReturnDate())
                .status(loan.getStatus())
                .build();
    }

    public LoanResponse updateLoan(Long id, LoanRequest request) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));


        if (request.getReturnDate() != null) {
            loan.setReturnDate(request.getReturnDate());
        }

        // Если нужно обновить что-то еще (например, статус вручную)
        // Здесь можно добавить проверку прав (только библиотекарь)

        return mapToResponse(loanRepository.save(loan));
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