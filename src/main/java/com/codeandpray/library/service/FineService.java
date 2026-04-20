package com.codeandpray.library.service;

import com.codeandpray.library.dto.FineRequest;
import com.codeandpray.library.dto.FineResponse;
import com.codeandpray.library.entity.Fine;
import com.codeandpray.library.mapper.FineMapper;
import com.codeandpray.library.repo.FineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepo fineRepo; // Исправлено имя переменной (с маленькой буквы)

    public List<FineResponse> findAll() {
        return fineRepo.findAll() // Теперь методы доступны благодаря JpaRepository
                .stream()
                .map(FineMapper::toResponse)
                .toList();
    }

    public FineResponse findById(Long id) {
        return FineMapper.toResponse(
                fineRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Штраф не найден"))
        );
    }

    @Transactional
    public FineResponse save(FineRequest dto) {
        Fine fine = new Fine();
        fine.setAmount(dto.getAmount());
        fine.setReason(dto.getReason());
        fine.setCreatedAt(LocalDateTime.now());
        fine.setStatus("UNPAID");

        return FineMapper.toResponse(fineRepo.save(fine));
    }

    @Transactional
    public FineResponse update(Long id, FineRequest dto) {
        Fine existingFine = fineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Штраф не найден"));

        if (dto.getAmount() != null) existingFine.setAmount(dto.getAmount());
        if (dto.getReason() != null) existingFine.setReason(dto.getReason());
        if (dto.getStatus() != null) existingFine.setStatus(dto.getStatus());

        return FineMapper.toResponse(fineRepo.save(existingFine));
    }

    public void deleteById(Long id) {
        if (!fineRepo.existsById(id)) {
            throw new RuntimeException("Штраф не найден");
        }
        fineRepo.deleteById(id);
    }
}