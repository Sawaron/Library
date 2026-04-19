package com.codeandpray.library.service;

import com.codeandpray.library.dto.UserRequest;
import com.codeandpray.library.dto.UserResponse;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.repo.UserRepo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;



    private UserResponse mapToResponse(User user) {
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

    private User mapToEntity(UserRequest request) {
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


    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse save(UserRequest request) {
        User user = mapToEntity(request);
        User savedUser = userRepo.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(Long id) {
        return userRepo.findById(id)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findByName(String name) {
        return userRepo.findByFirstname(name)
                .map(this::mapToResponse);
    }

    @Transactional
    public Optional<UserResponse> updateById(Long id, UserRequest updatedRequest) {
        return userRepo.findById(id)
                .map(oldUser -> {
                    oldUser.setFirstname(updatedRequest.getFirstname());
                    oldUser.setLastname(updatedRequest.getLastname());
                    oldUser.setEmail(updatedRequest.getEmail());
                    oldUser.setPhone(updatedRequest.getPhone());

                    if (updatedRequest.getPassword() != null && !updatedRequest.getPassword().isBlank()) {
                        oldUser.setPassword(updatedRequest.getPassword());
                    }

                    User savedUser = userRepo.save(oldUser);
                    return mapToResponse(savedUser);
                });
    }
}