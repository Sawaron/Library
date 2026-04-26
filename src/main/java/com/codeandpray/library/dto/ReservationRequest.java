package com.codeandpray.library.dto;




import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Reader ID is required")
    @JsonProperty("user_id")
    private Long readerId;

    private java.time.LocalDateTime reservationDate;
    private String status;
}