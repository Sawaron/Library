package com.codeandpray.library.controller;

import com.codeandpray.library.dto.NotificationRequest;
import com.codeandpray.library.dto.NotificationResponse;
import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.mapper.NotificationMapper;
import com.codeandpray.library.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }



    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getByUser(@PathVariable Long userId)  {
        return service.getByUser(userId)
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public NotificationResponse getById(@PathVariable Long id) {
        return NotificationMapper.toResponse(service.getById(id));
    }

    @PutMapping("/{id}")
    public NotificationResponse update(@PathVariable Long id, @RequestBody NotificationRequest req) {
        Notification notification = NotificationMapper.toEntity(req);
        Notification updated = service.update(id, notification);
        return NotificationMapper.toResponse((updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping
    public NotificationResponse create(@RequestBody NotificationRequest req) {
        Notification notification = NotificationMapper.toEntity(req);
        return NotificationMapper.toResponse(service.create(notification));
    }

    @GetMapping
    public List<NotificationResponse> getAll() {
        return service.getAll()
                .stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }


}
