package com.codeandpray.library.controller;

import com.codeandpray.library.dto.LoanRequest;
import com.codeandpray.library.dto.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.codeandpray.library.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public LoanResponse createLoan(@RequestBody LoanRequest request) {
        return loanService.createLoan(request);
    }

    @GetMapping
    public List<LoanResponse> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public LoanResponse getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @PostMapping("/{id}/return")
    public LoanResponse returnBook(@PathVariable Long id) {
        return loanService.returnBook(id);
    }

    @GetMapping("/reader/{readerId}")
    public List<LoanResponse> getReaderLoans(@PathVariable Long readerId) {
        return loanService.getLoansByReader(readerId);
    }
}