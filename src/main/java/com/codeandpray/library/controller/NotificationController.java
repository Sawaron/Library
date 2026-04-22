package com.codeandpray.library.controller;

import com.codeandpray.library.dto.NotificationResponse;
import com.codeandpray.library.entity.Notification;
import com.codeandpray.library.mapper.NotificationMapper;
import com.codeandpray.library.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationResponse create(@RequestBody NotificationResponse dto){
        Notification notification = NotificationMapper.toEntity(dto);
        Notification saved = service.create(notification);
        return NotificationMapper.toDto(saved);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getByUser(@PathVariable Long userId)  {
        return service.getByUser(userId)
                .stream()
                .map(NotificationMapper::toDto)
                .toList();
    }

    @GetMapping("/id")
    public NotificationResponse getById(@PathVariable Long id) {
        return NotificationMapper.toDto(service.getById(id));
    }

    @PutMapping("/{id}")
    public NotificationResponse update(@PathVariable Long id, @RequestBody NotificationResponse dto) {
        Notification notification = NotificationMapper.toEntity(dto);
        Notification updated = service.update(id, notification);
        return NotificationMapper.toDto((updated));
    }

    @DeleteMapping("/id")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
