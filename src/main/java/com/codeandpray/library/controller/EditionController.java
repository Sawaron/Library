package com.codeandpray.library.controller;

import com.codeandpray.library.dto.*;
import com.codeandpray.library.mapper.EditionMapper;
import com.codeandpray.library.service.EditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/editions")
@RequiredArgsConstructor
public class EditionController {

    private final EditionService editionService;
    private final EditionMapper editionMapper;

    @GetMapping
    public PageResponse<EditionResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return editionMapper.toPageResponse(editionService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public EditionResponse getById(@PathVariable Long id) {
        return editionMapper.toResponse(editionService.getById(id));
    }

    @PostMapping
    public EditionResponse create(@RequestBody CreateEditionRequest request) {
        return editionMapper.toResponse(editionService.create(request));
    }

    @PutMapping("/{id}")
    public EditionResponse update(@PathVariable Long id, @RequestBody UpdateEditionRequest request) {
        return editionMapper.toResponse(editionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        editionService.delete(id);
    }
}