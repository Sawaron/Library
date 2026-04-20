package com.codeandpray.library.controller;

import com.codeandpray.library.dto.FineRequest;
import com.codeandpray.library.dto.FineResponse;
import com.codeandpray.library.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fines") // Переносим версию сюда для всех методов сразу
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @GetMapping
    public List<FineResponse> findAll() {
        return fineService.findAll();
    }

    @PostMapping
    public ResponseEntity<FineResponse> createFine(@RequestBody FineRequest dto) {
        return new ResponseEntity<>(fineService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}") // Убираем лишние префиксы, оставляем только переменную пути
    public ResponseEntity<FineResponse> getFineById(@PathVariable Long id) {
        return ResponseEntity.ok(fineService.findById(id));
    }

    @PutMapping("/{id}") // Путь теперь будет /api/v1/fines/{id}
    public ResponseEntity<FineResponse> updateFine(@PathVariable Long id, @RequestBody FineRequest dto) {
        return ResponseEntity.ok(fineService.update(id, dto));
    }

    @DeleteMapping("/{id}") // Соответствует OpenAPI спецификации
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFine(@PathVariable Long id) {
        fineService.deleteById(id);
    }
}