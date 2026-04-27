package com.codeandpray.library.controller;

import com.codeandpray.library.dto.RatingRequest;
import com.codeandpray.library.dto.RatingResponse;
import com.codeandpray.library.service.RatingService;
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
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;


    @GetMapping("/all")
    public List<RatingResponse> findAll() {
        return ratingService.findAll();
    }

    @GetMapping("/book/{bookId}/all")
    public List<RatingResponse> findByBookId(@PathVariable Long bookId) {
        return ratingService.findByBookId(bookId);
    }

    @GetMapping("/user/{userId}/all")
    public List<RatingResponse> findByUserId(@PathVariable Long userId) {
        return ratingService.findByUserId(userId);
    }


    @GetMapping
    public Page<RatingResponse> findAllPaginated(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ratingService.findAllPaginated(pageable);
    }

    @GetMapping("/book/{bookId}")
    public Page<RatingResponse> findByBookIdPaginated(
            @PathVariable Long bookId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ratingService.findByBookIdPaginated(bookId, pageable);
    }

    @GetMapping("/user/{userId}")
    public Page<RatingResponse> findByUserIdPaginated(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ratingService.findByUserIdPaginated(userId, pageable);
    }

    @GetMapping("/score/{score}")
    public Page<RatingResponse> findByScore(
            @PathVariable Integer score,
            @PageableDefault(size = 20) Pageable pageable) {
        return ratingService.findByScore(score, pageable);
    }

    @GetMapping("/high")
    public Page<RatingResponse> findHighRatings(
            @RequestParam(defaultValue = "4") Integer minScore,
            @PageableDefault(size = 20, sort = "score", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ratingService.findHighRatings(minScore, pageable);
    }

    @GetMapping("/book/{bookId}/high")
    public Page<RatingResponse> findBookHighRatings(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "4") Integer minScore,
            @PageableDefault(size = 20, sort = "score", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ratingService.findBookHighRatings(bookId, minScore, pageable);
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