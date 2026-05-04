package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.UserRequest;
import com.codeandpray.library.dto.UserResponse;
import com.codeandpray.library.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {

    @Value("${library.user.default-role:READER}")
    private String defaultUserRole;

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .registrationDate(user.getRegistrationDate())
                .role(user.getRole())
                .build();
    }

    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }

        return User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .registrationDate(LocalDate.now())
                .role(defaultUserRole)
                .build();
    }

    public void updateEntity(User user, UserRequest request) {
        if (user == null || request == null) {
            return;
        }

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setBirthDate(request.getBirthDate());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }
    }
}