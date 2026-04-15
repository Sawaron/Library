package com.codeandpray.library.controller;

import com.codeandpray.library.dto.ReservationResponse;
import com.codeandpray.library.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponse create(
            @RequestParam Long bookId,
            @RequestParam Long readerId) {

        return reservationService.create(bookId, readerId);
    }
}