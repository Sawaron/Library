package com.codeandpray.library.service;

import com.codeandpray.library.dto.ReservationRequest;
import com.codeandpray.library.dto.ReservationResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Reservation;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.enums.ReservationStatus;
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.ReservationRepo;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepo reservationRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepository;

    @Transactional
    public ReservationResponse create(ReservationRequest request) {

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getCount() <= 0) {
            throw new RuntimeException("No copies available for reservation");
        }


        User user = userRepository.findById(request.getReaderId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        book.setCount(book.getCount() - 1);
        bookRepository.save(book);


        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }


    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ReservationResponse getById(Long id) {
        return reservationRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Transactional
    public ReservationResponse update(Long id, ReservationRequest request) {
        Reservation res = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));



        return mapToResponse(reservationRepository.save(res));
    }

    @Transactional
    public void cancel(Long id) {
        Reservation res = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (res.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Already cancelled");
        }

        res.setStatus(ReservationStatus.CANCELLED);


        Book book = res.getBook();
        book.setCount(book.getCount() + 1);
        bookRepository.save(book);

        reservationRepository.save(res);
    }

    private ReservationResponse mapToResponse(Reservation res) {
        return ReservationResponse.builder()
                .id(res.getId())
                .bookTitle(res.getBook().getTitle())
                .readerFullName(res.getUser().getFirstname() + " " + res.getUser().getLastname())
                .reservationDate(res.getReservationDate())
                .status(res.getStatus().name())
                .build();
    }
}