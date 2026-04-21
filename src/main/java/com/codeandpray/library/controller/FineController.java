package com.codeandpray.library.controller;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @GetMapping
    public PageResponse<FineResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return fineService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public FineResponse findById(@PathVariable Long id) {
        return fineService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FineResponse create(@RequestBody FineRequest dto) {
        return fineService.save(dto);
    }

    @PutMapping("/{id}")
    public FineResponse update(@PathVariable Long id, @RequestBody FineRequest dto) {
        return fineService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fineService.deleteById(id);
    }
}