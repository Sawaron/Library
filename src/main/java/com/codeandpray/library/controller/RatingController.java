package com.codeandpray.library.controller;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<RatingResponse> findAll() {
        return ratingService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse save(@RequestBody RatingRequest ratingRequest) {
        return ratingService.save(ratingRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> findById(@PathVariable Long id) {
        return ratingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/book/{bookId}")
    public List<RatingResponse> findByBookId(@PathVariable Long bookId) {
        return ratingService.findByBookId(bookId);
    }

    @GetMapping("/user/{userId}")
    public List<RatingResponse> findByUserId(@PathVariable Long userId) {
        return ratingService.findByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateById(@PathVariable Long id,
                                                     @RequestBody RatingRequest updatedRating) {
        return ratingService.updateById(id, updatedRating)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = ratingService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}