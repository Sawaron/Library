package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.NotificationRequest;
import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.dto.NotificationResponse;

public class NotificationMapper {

    public static NotificationResponse toResponse(Notification n) {
        NotificationResponse res = new NotificationResponse();
        res.setUserId(n.getUserId());
        res.setMessage(n.getMessage());
        res.setRead(n.isRead());
        res.setCreatedAt(n.getCreatedAt());
        return res;
    }

    public static Notification toEntity(NotificationRequest req){
        Notification n = new Notification();
        n.setUserId(req.getUserId());
        n.setMessage(req.getMessage());
        n.setRead(req.isRead());
        return n;
    }
}
