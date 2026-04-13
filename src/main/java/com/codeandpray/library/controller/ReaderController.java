package com.codeandpray.library.controller;

import com.codeandpray.library.entity.Reader;
import com.codeandpray.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}
