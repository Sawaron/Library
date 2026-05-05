package com.codeandpray.library.exception.entity;

public class NotificationNotFoundException extends EntityNotFoundException{
    public NotificationNotFoundException(String message) {
        super(message,"NOTIFICATION_NOT_FOUND");
    }
}
