package com.codeandpray.library.service;

import com.codeandpray.library.entity.Fine;
import com.codeandpray.library.repo.FineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepo fineRepository;

    public List<Fine> findAll() {
        return fineRepository.findAll();
    }

    public Fine findById(Long id) {
        return fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Штраф не найден"));
    }

    public Fine save(Fine fine) {
        return fineRepository.save(fine);
    }

    public void deleteById(Long id) {
        fineRepository.deleteById(id);
    }
}