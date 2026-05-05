package com.codeandpray.library.service;

import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.repo.NotificationRepo;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;


    public Notification create(Notification notification) {
        return notificationRepo.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> getAll() {
        return notificationRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Notification getById(Long id) {
        return notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Уведомление с ID " + id + " не найдено"));
    }

    @Transactional(readOnly = true)
    public List<Notification> getByUserFirstname(String firstname) {
        User user = userRepo.findByFirstname(firstname)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + firstname));
        return notificationRepo.findByUserId(user.getId());
    }

    public void markAsRead(Long id, String currentUserFirstname) {
        Notification notification = getById(id);
        User user = userRepo.findByFirstname(currentUserFirstname).orElseThrow();

        if (!notification.getUserId().equals(user.getId())) {
            throw new RuntimeException("Вы не можете изменять чужие уведомления!");
        }

        notification.setRead(true);
        notificationRepo.save(notification);
    }

    public void delete(Long id) {
        if (!notificationRepo.existsById(id)) {
            throw new RuntimeException("Нечего удалять: уведомление не найдено");
        }
        notificationRepo.deleteById(id);
    }
}