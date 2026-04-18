package com.codeandpray.library.service;

import com.codeandpray.library.dto.ReservationResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.entity.Reservation;
import com.codeandpray.library.enums.BookStatus;
import com.codeandpray.library.enums.ReservationStatus;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.ReservationRepo;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;

    @Transactional
    public ReservationResponse create(Long bookId, Long userId) {

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Книга уже выдана или забронирована");
        }

        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);


        book.setStatus(BookStatus.UNAVAILABLE);
        bookRepo.save(book);


        Reservation saved = reservationRepo.save(reservation);

        return ReservationResponse.builder()
                .id(saved.getId())
                .bookTitle(book.getTitle())
                .readerFullName(user.getFirstname() + " " + user.getLastname())
                .reservationDate(saved.getReservationDate())
                .status(saved.getStatus().name())
                .build();
    }
}