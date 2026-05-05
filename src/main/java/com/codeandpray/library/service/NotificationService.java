package com.codeandpray.library.service;

import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.repo.NotificationRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
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

    public List<Notification> getAll() {
        return repo.findAll();
    }

    public Notification getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));



    }

    public Notification update(Long id, Notification newData) {
        Notification existing = repo.findById(id).orElseThrow();

        existing.setMessage(newData.getMessage());
        existing.setUserId(newData.getUserId());
        existing.setRead(newData.isRead());
        existing.setType(newData.getType());

        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
