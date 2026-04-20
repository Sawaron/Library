package com.codeandpray.library.service;

import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.repo.NotificationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepo repo;

    public NotificationService(NotificationRepo repo){
        this.repo = repo;
    }

    public Notification create(Notification notification) {
        return repo.save(notification);
    }

    public List<Notification> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public void markAsRead(Long id) {
        Notification n = repo.findById(id).orElseThrow();
        n.setRead(true);
        repo.save(n);
    }
}
