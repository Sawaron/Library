package com.codeandpray.library.controller;

import com.codeandpray.library.dto.NotificationDto;
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
    public NotificationDto create(@RequestBody NotificationDto dto){
        Notification notification = NotificationMapper.toEntity(dto);
        Notification saved = service.create(notification);
        return NotificationMapper.toDto(saved);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationDto> getByUser(@PathVariable Long userId)  {
        return service.getByUser(userId)
                .stream()
                .map(NotificationMapper::toDto)
                .toList();
    }

    @GetMapping("/id")
    public NotificationDto getById(@PathVariable Long id) {
        return NotificationMapper.toDto(service.getById(id));
    }

    @PutMapping("/{id}")
    public NotificationDto update(@PathVariable Long id, @RequestBody NotificationDto dto) {
        Notification notification = NotificationMapper.toEntity(dto);
        Notification updated = service.update(id, notification);
        return NotificationMapper.toDto((updated));
    }

    @DeleteMapping("/id")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
