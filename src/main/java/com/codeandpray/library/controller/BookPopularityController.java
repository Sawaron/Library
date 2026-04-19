package com.codeandpray.library.controller;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.enums.BookPopularityPeriod;
import com.codeandpray.library.service.BookPopularityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-popularities")
@RequiredArgsConstructor
public class BookPopularityController {

    private final BookPopularityService bookPopularityService;

    @GetMapping
    public List<BookPopularityResponse> findAll() {
        return bookPopularityService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookPopularityResponse save(@RequestBody BookPopularityRequest request) {
        return bookPopularityService.save(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookPopularityResponse> findById(@PathVariable Long id) {
        return bookPopularityService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/book/{bookId}")
    public List<BookPopularityResponse> findByBookId(@PathVariable Long bookId) {
        return bookPopularityService.findByBookId(bookId);
    }

    @GetMapping("/period/{period}")
    public List<BookPopularityResponse> findByPeriod(@PathVariable BookPopularityPeriod period) {
        return bookPopularityService.findByPeriod(period);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookPopularityResponse> updateById(@PathVariable Long id,
                                                             @RequestBody BookPopularityRequest updatedRequest) {
        return bookPopularityService.updateById(id, updatedRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = bookPopularityService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/book/{bookId}/read")
    @ResponseStatus(HttpStatus.OK)
    public void incrementReadCount(@PathVariable Long bookId, @RequestParam BookPopularityPeriod period) {
        bookPopularityService.incrementReadCount(bookId, period);
    }
}