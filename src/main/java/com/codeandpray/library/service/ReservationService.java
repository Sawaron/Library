package com.codeandpray.library.service;

import com.codeandpray.library.dto.PageResponse;
import com.codeandpray.library.dto.ReservationRequest;
import com.codeandpray.library.dto.ReservationResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Reservation;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.enums.ReservationStatus;
import com.codeandpray.library.mapper.ReservationMapper; // Импортируем твой маппер
import com.codeandpray.library.repo.BookRepo;
import com.codeandpray.library.repo.ReservationRepo;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepo reservationRepository;
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final ReservationMapper reservationMapper;

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
        reservation.setStatus(ReservationStatus.ACTIVE);

        Reservation saved = reservationRepository.save(reservation);


        return reservationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PageResponse<ReservationResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);


        Page<ReservationResponse> responsePage = reservationPage.map(reservationMapper::toResponse);
        return PageResponse.of(responsePage);
    }

    @Transactional(readOnly = true)
    public ReservationResponse getById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Transactional
    public ReservationResponse update(Long id, ReservationRequest request) {
        Reservation res = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));

        if (request.getReservationDate() != null) {
            res.setReservationDate(request.getReservationDate());
        }

        if (request.getStatus() != null) {
            try {
                res.setStatus(ReservationStatus.valueOf(request.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status: " + request.getStatus());
            }
        }

        Reservation updated = reservationRepository.save(res);
        return reservationMapper.toResponse(updated);
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


}