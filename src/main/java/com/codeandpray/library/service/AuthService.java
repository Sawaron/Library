package com.codeandpray.library.service;

import com.codeandpray.library.dto.LoginRequest;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.repo.UserRepo;
import com.codeandpray.library.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    private static final String LIBRARIAN_SECRET = "MY_SUPER_SECRET_123";

    @Transactional
    public void register(LoginRequest request) {
        String role = LIBRARIAN_SECRET.equals(request.getSecretCode())
                ? "LIBRARIAN"
                : "READER";

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepo.save(user);
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getFirstname(), request.getPassword())
        );

        User user = userRepo.findByFirstname(request.getFirstname()).orElseThrow();
        return tokenProvider.createToken(user.getFirstname(), List.of("ROLE_" + user.getRole()));
    }
}