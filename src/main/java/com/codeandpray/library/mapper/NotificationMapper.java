package com.codeandpray.library.mapper;

import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.dto.NotificationResponse;

public class NotificationMapper {

    public static NotificationResponse toDto(Notification notification) {
        NotificationResponse dto = new NotificationResponse();

        dto.setUniqueId(notification.getUniqueId());
        dto.setUserId(notification.getUserId());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());

        return dto;
    }

    public static Notification toEntity(NotificationResponse dto){
        Notification notification = new Notification();

        notification.setUniqueId(dto.getUniqueId());
        notification.setUserId(dto.getUserId());
        notification.setMessage(dto.getMessage());
        notification.setRead(dto.isRead());

        return notification;


    }
}
