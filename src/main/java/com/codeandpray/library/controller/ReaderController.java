package com.codeandpray.library.controller;

import com.codeandpray.library.entity.Reader;
import com.codeandpray.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;


    @GetMapping
    public List<Reader> findAll() {
        return readerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reader save(@RequestBody Reader reader) {
        return readerService.save(reader);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> findById(@PathVariable Long id) {
        return readerService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Reader> findByName(@PathVariable String name) {
        return readerService.findByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateById(@PathVariable Long id, @RequestBody Reader updatedReader) {
        return readerService.updateById(id, updatedReader).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
