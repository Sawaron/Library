package com.codeandpray.library.controller;

import com.codeandpray.library.dto.JwtResponse;
import com.codeandpray.library.dto.LoginRequest;
import com.codeandpray.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody LoginRequest request) {
        authService.register(request);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return new JwtResponse(token);
    }
}