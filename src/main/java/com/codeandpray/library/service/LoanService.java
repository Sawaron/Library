package com.codeandpray.library.service;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.dto.LoanResponse;
import com.codeandpray.library.dto.PageResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Loan;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.enums.LoanStatus;
import com.codeandpray.library.exception.entity.BookNotFoundException;
import com.codeandpray.library.exception.entity.LoanNotFoundException;
import com.codeandpray.library.exception.entity.UserNotFoundException;
import com.codeandpray.library.exception.logic.LoanAlreadyReturned;
import com.codeandpray.library.exception.logic.LogicBadRequestException;
import com.codeandpray.library.mapper.LoanMapper;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.LoanRepo;
import com.codeandpray.library.repo.UserRepo;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@Service
public class LoanService {

    private final LoanRepo loanRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final LoanMapper loanMapper;
    private final FineService fineService;
    private final AgeRestrictionService ageRestrictionService;

    private final Counter loansCreated;
    private final Counter loansReturned;
    private final Counter loansCancelled;

    public LoanService(LoanRepo loanRepository, BookRepo bookRepository,
                       UserRepo userRepository, LoanMapper loanMapper,
                       FineService fineService,
                       AgeRestrictionService ageRestrictionService,
                       MeterRegistry meterRegistry) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanMapper = loanMapper;
        this.fineService = fineService;
        this.ageRestrictionService = ageRestrictionService;


        this.loansCreated = Counter.builder("library.loans.created")
                .description("Количество выданных книг").register(meterRegistry);
        this.loansReturned = Counter.builder("library.loans.returned")
                .description("Количество возвращённых книг").register(meterRegistry);
        this.loansCancelled = Counter.builder("library.loans.cancelled")
                .description("Количество отменённых выдач").register(meterRegistry);
    }

    @Transactional
    public LoanResponse createLoan(LoanRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Книга с ID: " + request.getBookId() + " не найдено"));

        User user = userRepository.findById(request.getReaderId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID: " + request.getReaderId() + " не найден"));

        ageRestrictionService.checkUserCanAccessBook(user, book);

        if (book.getCount() <= 0) {
            throw new LogicBadRequestException("В данный момент нет свободных экземпляров книги для выдачи", "BOOK_NOT_AVAILABLE");
        }


        LocalDate autoReturnDate = LocalDate.now().plusDays(14);

        Loan loan = Loan.builder()
                .book(book)
                .user(user)
                .loanDate(LocalDate.now())
                .returnDate(autoReturnDate)
                .status(LoanStatus.ISSUED)
                .build();

        book.setCount(book.getCount() - 1);
        bookRepository.save(book);

        Loan savedLoan = loanRepository.save(loan);
        loansCreated.increment();
        return loanMapper.toResponse(savedLoan);
    }

    @Transactional
    public LoanResponse updateLoan(Long id, LoanRequest request) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Запись о выдаче с ID: " + id + " не найдена"));

        if (request.getReturnDate() != null) {
            loan.setReturnDate(request.getReturnDate());
        }

        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public PageResponse<LoanResponse> getAllLoans(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return PageResponse.of(loanRepository.findAll(pageable).map(loanMapper::toResponse));
    }

    @Transactional(readOnly = true)
    public LoanResponse getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(loanMapper::toResponse)
                .orElseThrow(() -> new LoanNotFoundException("Запись о выдаче с ID: " + id + " не найдена"));
    }

    @Transactional
    public LoanResponse returnBook(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Запись о выдаче с ID: " + id + " не найдена"));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturned("Книга c ID: " + id + " уже возвращена");
        }


        if (LocalDate.now().isAfter(loan.getReturnDate())) {
            fineService.generateOverDueFine(loan);
        }

        loan.setActualReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);

        Book book = loan.getBook();
        book.setCount(book.getCount() + 1);
        bookRepository.save(book);

        loansReturned.increment();
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public PageResponse<LoanResponse> getLoansByReader(Long readerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Loan> loanPage = loanRepository.findByUserId(readerId, pageable);
        return PageResponse.of(loanPage.map(loanMapper::toResponse));
    }

    @Transactional
    public void cancelLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Запись о выдаче с ID: " + id + " не найдена"));

        if (loan.getStatus() == LoanStatus.RETURNED || loan.getStatus() == LoanStatus.CANCELLED) {
            throw new LogicBadRequestException("Невозможно отменить заём так как он уже завершен или был отменен ранее","LOAN_ALREADY_PROCESSED");
        }

        loan.setStatus(LoanStatus.CANCELLED);

        Book book = loan.getBook();
        book.setCount(book.getCount() + 1);
        bookRepository.save(book);

        loanRepository.save(loan);
        loansCancelled.increment();
    }
}