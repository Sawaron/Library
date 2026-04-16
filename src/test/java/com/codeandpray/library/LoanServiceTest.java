package com.codeandpray.library;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.entity.Loan;
import com.codeandpray.library.repo.LoanRepository;
import com.codeandpray.library.service.LoanService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    private final LoanRepository loanRepository = mock(LoanRepository.class);
    private final LoanService loanService = new LoanService(loanRepository);

    //  1. Создание выдачи
    @Test
    void shouldCreateLoan() {
        LoanRequest request = LoanRequest.builder()
                .bookId(1L)
                .bookTitle("Java")
                .readerId(1L)
                .readerName("Test")
                .returnDate(LocalDate.now().plusDays(7))
                .build();

        when(loanRepository.findByBookIdAndStatus(1L, "ACTIVE"))
                .thenReturn(Optional.empty());

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = loanService.createLoan(request);

        assertNotNull(response);
        assertEquals(1L, response.getBookId());
        assertEquals("ACTIVE", response.getStatus());
    }

    //  2. Ошибка если книга уже выдана
    @Test
    void shouldThrowExceptionIfBookAlreadyLoaned() {
        LoanRequest request = LoanRequest.builder()
                .bookId(1L)
                .build();

        when(loanRepository.findByBookIdAndStatus(1L, "ACTIVE"))
                .thenReturn(Optional.of(new Loan()));

        assertThrows(RuntimeException.class, () -> {
            loanService.createLoan(request);
        });
    }

    //  3. Возврат книги
    @Test
    void shouldReturnBook() {
        Loan loan = Loan.builder()
                .id(1L)
                .status("ACTIVE")
                .build();

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = loanService.returnBook(1L);

        assertEquals("RETURNED", response.getStatus());
        assertNotNull(response.getActualReturnDate());
    }

    //  4. Получение по ID
    @Test
    void shouldGetLoanById() {
        Loan loan = Loan.builder()
                .id(1L)
                .bookId(1L)
                .status("ACTIVE")
                .build();

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        var response = loanService.getLoanById(1L);

        assertEquals(1L, response.getId());
    }
}