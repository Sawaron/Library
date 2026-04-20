package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.ReservationResponse;
import com.codeandpray.library.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(Reservation reservation) {
        if (reservation == null) return null;

        return ReservationResponse.builder()
                .id(reservation.getId())
                .bookTitle(reservation.getBook().getTitle()) // Берём заголовок
                .readerFullName(reservation.getUser().getFirstname() + " " + reservation.getUser().getLastname()) // Склеиваем имя
                .reservationDate(reservation.getReservationDate())
                .status(String.valueOf(reservation.getStatus()))
                .build();
    }
}