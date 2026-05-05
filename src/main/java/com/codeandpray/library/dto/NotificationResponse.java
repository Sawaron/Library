package com.codeandpray.library.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NotificationResponse {
    private Long userId;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;


}
