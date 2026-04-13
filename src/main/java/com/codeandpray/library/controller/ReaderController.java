package com.codeandpray.library.controller;

import com.codeandpray.library.entity.Reader;
import com.codeandpray.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reader createReader(@RequestBody Reader reader) {
        return readerService.createReader(reader);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        return readerService.getReaderById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<Reader> getReaderByName(String name) {
        return readerService.getReaderByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("byId/{id}")
    public ResponseEntity<Reader> updateUser(Long id, Reader updatedReader) {
        return readerService.updateReader(id, updatedReader).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
