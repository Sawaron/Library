package com.codeandpray.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NotificationRequest {
    private Long uniqueId;
    private Long userId;
    private String message;
    private boolean isRead;

}
