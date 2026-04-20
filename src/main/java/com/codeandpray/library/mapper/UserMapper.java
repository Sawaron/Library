package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.UserRequest;
import com.codeandpray.library.dto.UserResponse;
import com.codeandpray.library.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

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
                .password(request.getPassword())
                .registrationDate(LocalDate.now())
                .role("READER")
                .build();
    }

    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return List.of();
        }

        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(User user, UserRequest request) {
        if (user == null || request == null) {
            return;
        }

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }
    }
}