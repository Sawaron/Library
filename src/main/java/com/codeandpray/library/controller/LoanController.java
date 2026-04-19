package com.codeandpray.library.controller;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.dto.LoanResponse;
import com.codeandpray.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanRequest request) {
        // Возвращаем 201 Created по просьбе аналитиков
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(request));
    }

    @GetMapping
    public List<LoanResponse> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public LoanResponse getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @PutMapping("/{id}")
    public LoanResponse updateLoan(@PathVariable Long id, @RequestBody LoanRequest request) {
        return loanService.updateLoan(id, request);
    }

    // Метод возврата книги
    @PatchMapping("/{id}/return")
    public LoanResponse returnBook(@PathVariable Long id) {
        return loanService.returnBook(id);
    }

    // Метод отмены (Soft Delete)
    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelLoan(@PathVariable Long id) {
        loanService.cancelLoan(id);
    }

    // История для конкретного пользователя
    @GetMapping("/reader/{readerId}")
    public List<LoanResponse> getLoansByReader(@PathVariable Long readerId) {
        return loanService.getLoansByReader(readerId);
    }
}