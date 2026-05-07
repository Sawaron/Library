package com.codeandpray.library.controller;

import com.codeandpray.library.dto.UserRequest;
import com.codeandpray.library.dto.UserResponse;
import com.codeandpray.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/paginated")
    public Page<UserResponse> findAllPaginated(Pageable pageable) {
        return userService.findAllPaginated(pageable);
    }

    @GetMapping("/search")
    public Page<UserResponse> searchByName(@RequestParam String name, Pageable pageable) {
        return userService.findByNameContaining(name, pageable);
    }

    @GetMapping("/search/lastname")
    public Page<UserResponse> searchByLastname(@RequestParam String lastname, Pageable pageable) {
        return userService.findByLastnameContaining(lastname, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse save(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponse> findByName(@PathVariable String name) {
        return userService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lastname/{lastname}")  // ← Новый эндпоинт
    public ResponseEntity<UserResponse> findByLastname(@PathVariable String lastname) {
        return userService.findByLastname(lastname)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateById(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}