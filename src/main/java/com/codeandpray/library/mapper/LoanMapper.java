package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.LoanResponse;
import com.codeandpray.library.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponse toResponse(Loan loan) {
        if (loan == null) return null;

        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBook().getId())
                .bookTitle(loan.getBook().getTitle())
                .readerId(loan.getUser().getId())
                .readerName(loan.getUser().getFirstname() + " " + loan.getUser().getLastname())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getLoanDate().plusDays(14))
                .actualReturnDate(loan.getActualReturnDate())
                .status(String.valueOf(loan.getStatus()))
                .build();
    }
}