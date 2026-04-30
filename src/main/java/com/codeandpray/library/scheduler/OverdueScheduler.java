package com.codeandpray.library.scheduler;

import com.codeandpray.library.entity.Loan;
import com.codeandpray.library.enums.LoanStatus;
import com.codeandpray.library.repo.LoanRepo;
import com.codeandpray.library.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class OverdueScheduler {

    private final LoanRepo loanRepo;
    private final FineService fineService;

    @Scheduled(cron = "0 1 1 * * *")
    public void processOverdueLoans() {
        List<Loan> overdueLoans = loanRepo.findAllByStatusAndReturnDateBefore(
                LoanStatus.ISSUED,
                LocalDate.now()
        );

        for (Loan loan : overdueLoans) {
            fineService.generateOverDueFine(loan);
        }
    }
}