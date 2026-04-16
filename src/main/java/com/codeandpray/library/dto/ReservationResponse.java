package com.codeandpray.library.dto;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;

@Value
@Builder
public class ReservationResponse {
    Long id;
    String bookTitle;
    String readerFullName;
    LocalDateTime reservationDate;
    String status;
}