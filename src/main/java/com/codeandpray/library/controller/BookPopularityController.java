package com.codeandpray.library.controller;

import com.codeandpray.library.dto.BookPopularityRequest;
import com.codeandpray.library.dto.BookPopularityResponse;
import com.codeandpray.library.enums.BookPopularityPeriod;
import com.codeandpray.library.service.BookPopularityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-popularities")
@RequiredArgsConstructor
public class BookPopularityController {

    private final BookPopularityService bookPopularityService;


    @GetMapping("/all")
    public List<BookPopularityResponse> findAll() {
        return bookPopularityService.findAll();
    }

    @GetMapping("/book/{bookId}/all")
    public List<BookPopularityResponse> findByBookId(@PathVariable Long bookId) {
        return bookPopularityService.findByBookId(bookId);
    }

    @GetMapping("/period/{period}/all")
    public List<BookPopularityResponse> findByPeriod(@PathVariable BookPopularityPeriod period) {
        return bookPopularityService.findByPeriod(period);
    }


    @GetMapping
    public Page<BookPopularityResponse> findAllPaginated(
            @PageableDefault(size = 20, sort = "readCount", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return bookPopularityService.findAllPaginated(pageable);
    }

    @GetMapping("/book/{bookId}")
    public Page<BookPopularityResponse> findByBookIdPaginated(
            @PathVariable Long bookId,
            @PageableDefault(size = 20, sort = "period", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return bookPopularityService.findByBookIdPaginated(bookId, pageable);
    }

    @GetMapping("/period/{period}")
    public Page<BookPopularityResponse> findByPeriodPaginated(
            @PathVariable BookPopularityPeriod period,
            @PageableDefault(size = 20, sort = "readCount", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return bookPopularityService.findByPeriodPaginated(period, pageable);
    }

    @GetMapping("/popular")
    public Page<BookPopularityResponse> findPopularBooks(
            @RequestParam(defaultValue = "100") Integer minReadCount,
            @PageableDefault(size = 20, sort = "readCount", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return bookPopularityService.findPopularBooks(minReadCount, pageable);
    }

    @GetMapping("/period/{period}/popular")
    public Page<BookPopularityResponse> findPopularByPeriod(
            @PathVariable BookPopularityPeriod period,
            @RequestParam(defaultValue = "100") Integer minReadCount,
            @PageableDefault(size = 20, sort = "readCount", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return bookPopularityService.findPopularByPeriod(period, minReadCount, pageable);
    }

    @GetMapping("/most-popular")
    public Page<BookPopularityResponse> findMostPopular(
            @PageableDefault(size = 10) Pageable pageable) {
        return bookPopularityService.findMostPopular(pageable);
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
    public void incrementReadCount(@PathVariable Long bookId,
                                   @RequestParam BookPopularityPeriod period) {
        bookPopularityService.incrementReadCount(bookId, period);
    }
}