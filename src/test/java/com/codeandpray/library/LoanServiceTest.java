package com.codeandpray.library;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.dto.LoanResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Loan;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.enums.AgeCategory;
import com.codeandpray.library.enums.LoanStatus;
import com.codeandpray.library.exception.entity.LoanNotFoundException;
import com.codeandpray.library.exception.logic.LoanAlreadyReturned;
import com.codeandpray.library.mapper.LoanMapper;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.LoanRepo;
import com.codeandpray.library.repo.UserRepo;
import com.codeandpray.library.service.AgeRestrictionService;
import com.codeandpray.library.service.FineService;
import com.codeandpray.library.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock private LoanRepo loanRepository;
    @Mock private BookRepo bookRepository;
    @Mock private UserRepo userRepository;
    @Mock private LoanMapper loanMapper;
    @Mock private FineService fineService;
    @Mock private AgeRestrictionService ageRestrictionService;

    @InjectMocks
    private LoanService loanService;

    private User adultUser;
    private Book regularBook;

    @BeforeEach
    void setUp() {
        adultUser = User.builder()
                .id(1001L)
                .firstname("Иван")
                .lastname("Петров")
                .role("READER")
                .birthDate(LocalDate.of(1995, 5, 15))
                .build();

        regularBook = Book.builder()
                .id(42L)
                .title("Преступление и наказание")
                .ageCategory(AgeCategory.SIXTEEN_PLUS)
                .count(3)
                .build();
    }

    // TC-01: Возврат просроченной книги → штраф
    @Test
    @DisplayName("TC-01: Просроченный возврат генерирует штраф")
    void returnOverdueBook_shouldGenerateFine() {
        Loan overdueLoan = Loan.builder()
                .id(1L)
                .book(regularBook)
                .user(adultUser)
                .loanDate(LocalDate.of(2026, 4, 1))
                .returnDate(LocalDate.of(2026, 4, 10))
                .status(LoanStatus.ISSUED)
                .build();

        when(loanRepository.findById(1L)).thenReturn(Optional.of(overdueLoan));
        when(loanRepository.save(any())).thenReturn(overdueLoan);
        when(bookRepository.save(any())).thenReturn(regularBook);
        when(loanMapper.toResponse(any())).thenReturn(LoanResponse.builder().id(1L).status("RETURNED").build());

        LoanResponse result = loanService.returnBook(1L);

        assertThat(result.getStatus()).isEqualTo("RETURNED");
        verify(fineService, times(1)).generateOverDueFine(overdueLoan);
    }

    // TC-01b: Своевременный возврат → штраф НЕ создаётся
    @Test
    @DisplayName("TC-01b: Своевременный возврат — штраф не начисляется")
    void returnBookOnTime_shouldNotGenerateFine() {
        Loan onTimeLoan = Loan.builder()
                .id(2L)
                .book(regularBook)
                .user(adultUser)
                .loanDate(LocalDate.now().minusDays(5))
                .returnDate(LocalDate.now().plusDays(9))
                .status(LoanStatus.ISSUED)
                .build();

        when(loanRepository.findById(2L)).thenReturn(Optional.of(onTimeLoan));
        when(loanRepository.save(any())).thenReturn(onTimeLoan);
        when(bookRepository.save(any())).thenReturn(regularBook);
        when(loanMapper.toResponse(any())).thenReturn(LoanResponse.builder().status("RETURNED").build());

        loanService.returnBook(2L);

        verify(fineService, never()).generateOverDueFine(any());
    }

    // TC-01c: Повторный возврат → 409
    @Test
    @DisplayName("TC-01c: Повторный возврат книги → LoanAlreadyReturned (409)")
    void returnAlreadyReturnedBook_shouldThrow409() {
        Loan alreadyReturned = Loan.builder()
                .id(3L)
                .book(regularBook)
                .user(adultUser)
                .status(LoanStatus.RETURNED)
                .build();

        when(loanRepository.findById(3L)).thenReturn(Optional.of(alreadyReturned));

        assertThatThrownBy(() -> loanService.returnBook(3L))
                .isInstanceOf(LoanAlreadyReturned.class);

        verify(fineService, never()).generateOverDueFine(any());
    }

    // TC-03: Несуществующий заём → 404
    @Test
    @DisplayName("TC-03: Запрос несуществующего займа → LoanNotFoundException (404)")
    void getLoanById_notFound_shouldThrow404() {
        when(loanRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.getLoanById(999L))
                .isInstanceOf(LoanNotFoundException.class);
    }

    // TC-04: count уменьшается при выдаче
    @Test
    @DisplayName("TC-04: При выдаче book.count уменьшается на 1")
    void createLoan_shouldDecrementBookCount() {
        LoanRequest request = new LoanRequest();
        request.setBookId(42L);
        request.setReaderId(1001L);

        when(bookRepository.findById(42L)).thenReturn(Optional.of(regularBook));
        when(userRepository.findById(1001L)).thenReturn(Optional.of(adultUser));
        doNothing().when(ageRestrictionService).checkUserCanAccessBook(any(), any());

        Loan savedLoan = Loan.builder()
                .id(10L).book(regularBook).user(adultUser)
                .loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(14))
                .status(LoanStatus.ISSUED).build();

        when(loanRepository.save(any())).thenReturn(savedLoan);
        when(bookRepository.save(any())).thenReturn(regularBook);
        when(loanMapper.toResponse(any())).thenReturn(
                LoanResponse.builder().id(10L).status("ISSUED").build());

        loanService.createLoan(request);

        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookCaptor.capture());
        assertThat(bookCaptor.getValue().getCount()).isEqualTo(2);
    }

    // TC-04b: count увеличивается при возврате
    @Test
    @DisplayName("TC-04b: При возврате book.count увеличивается на 1")
    void returnBook_shouldIncrementBookCount() {
        regularBook.setCount(2);
        Loan loan = Loan.builder()
                .id(5L)
                .book(regularBook)
                .user(adultUser)
                .loanDate(LocalDate.now().minusDays(3))
                .returnDate(LocalDate.now().plusDays(11))
                .status(LoanStatus.ISSUED)
                .build();

        when(loanRepository.findById(5L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);
        when(bookRepository.save(any())).thenReturn(regularBook);
        when(loanMapper.toResponse(any())).thenReturn(
                LoanResponse.builder().status("RETURNED").build());

        loanService.returnBook(5L);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertThat(captor.getValue().getCount()).isEqualTo(3);
    }
}
