package com.codeandpray.library.controller;

import com.codeandpray.library.dto.NotificationRequest;
import com.codeandpray.library.dto.NotificationResponse;
import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.mapper.NotificationMapper;
import com.codeandpray.library.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public List<NotificationResponse> getMyNotifications(Authentication auth) {
        return notificationService.getByUserFirstname(auth.getName())
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public NotificationResponse create(@RequestBody NotificationRequest req) {
        Notification notification = NotificationMapper.toEntity(req);
        return NotificationMapper.toResponse(notificationService.create(notification));
    }

    @PatchMapping("/{id}/read")
    @PreAuthorize("isAuthenticated()")
    public void markAsRead(@PathVariable Long id, Authentication auth) {
        notificationService.markAsRead(id, auth.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<NotificationResponse> getAll() {
        return notificationService.getAll()
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }
}