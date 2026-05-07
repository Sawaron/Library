package com.codeandpray.library.service;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.entity.*;
import com.codeandpray.library.enums.FineStatus;
import com.codeandpray.library.exception.entity.FineNotFoundException;
import com.codeandpray.library.exception.entity.LoanNotFoundException;
import com.codeandpray.library.exception.logic.LogicBadRequestException;
import com.codeandpray.library.mapper.FineMapper;
import com.codeandpray.library.repo.FineRepo;
import com.codeandpray.library.repo.LoanRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepo fineRepo;
    private final LoanRepo loanRepo;
    private final FineMapper fineMapper;

    @Value("${library.fine.daily-rate:10.0}")
    private double dailyFineRate;

    @Transactional(readOnly = true)
    public PageResponse<FineResponse> findAll(int page, int size) {
        var pageable = PageRequest.of(page, size);
        return PageResponse.of(fineRepo.findAll(pageable).map(fineMapper::toResponse));
    }

    @Transactional(readOnly = true)
    public FineResponse findById(Long id) {
        return fineRepo.findById(id)
                .map(fineMapper::toResponse)
                .orElseThrow(() -> new FineNotFoundException("Штраф с ID: " + id + " не найден"));
    }

    @Transactional
    public FineResponse save(FineRequest dto) {
        Loan loan = loanRepo.findById(dto.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException("Заем с ID: " + dto.getLoanId() + " не найден"));

        Fine fine = new Fine();
        fine.setLoan(loan);
        fine.setAmount(dto.getAmount());
        fine.setReason(dto.getReason());
        fine.setCreatedAt(LocalDateTime.now());
        fine.setStatus(FineStatus.UNPAID);

        return fineMapper.toResponse(fineRepo.save(fine));
    }

    @Transactional
    public FineResponse update(Long id, FineRequest dto) {
        Fine fine = fineRepo.findById(id)
                .orElseThrow(() -> new FineNotFoundException("Штраф с ID:" + id + " не найден"));

        if (dto.getAmount() != null) fine.setAmount(dto.getAmount());
        if (dto.getReason() != null) fine.setReason(dto.getReason());
        if (dto.getStatus() != null) fine.setStatus(FineStatus.valueOf(dto.getStatus()));

        return fineMapper.toResponse(fineRepo.save(fine));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!fineRepo.existsById(id)) {
            throw new FineNotFoundException("Штраф с ID: " + id + " не найден");
        }
        fineRepo.deleteById(id);
    }

    @Transactional
    public void generateOverDueFine(Loan loan) {
        LocalDate today = LocalDate.now();
        LocalDate returnDate = loan.getReturnDate();

        if (returnDate == null) {
            throw new LogicBadRequestException("Невозможно рассчитать штраф: дата возврата займа не установлена", "INVALID_LOAN_STATE");
        }

        long daysOverdue = ChronoUnit.DAYS.between(returnDate, today);

        if (daysOverdue > 0) {
            double amount = daysOverdue * dailyFineRate;

            Fine fine = new Fine();
            fine.setLoan(loan);
            fine.setAmount(BigDecimal.valueOf(amount));
            fine.setReason("Overdue: " + daysOverdue + " days past return date");
            fine.setCreatedAt(LocalDateTime.now());
            fine.setStatus(FineStatus.UNPAID);

            fineRepo.save(fine);
        }
    }
}