package com.codeandpray.library.service;

import com.codeandpray.library.dto.UserRequest;
import com.codeandpray.library.dto.UserResponse;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.exception.entity.EntityNotFoundException;
import com.codeandpray.library.exception.entity.UserNotFoundException;
import com.codeandpray.library.exception.logic.LogicBadRequestException;
import com.codeandpray.library.mapper.UserMapper;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<UserResponse> findAllPaginated(Pageable pageable) {
        return userRepo.findAll(pageable).map(userMapper::toResponse);
    }

    public Page<UserResponse> findByNameContaining(String name, Pageable pageable) {
        return userRepo.findByFirstnameContaining(name, pageable).map(userMapper::toResponse);
    }

    public Page<UserResponse> findByLastnameContaining(String lastname, Pageable pageable) {
        return userRepo.findByLastnameContaining(lastname, pageable).map(userMapper::toResponse);
    }

    public UserResponse findById(Long id) {
        return userRepo.findById(id).map(userMapper::toResponse).orElseThrow(() -> new UserNotFoundException("Пользователь с ID: " + id + " не найден"));
    }

    public Optional<UserResponse> findByName(String name) {
        return userRepo.findByFirstname(name).map(userMapper::toResponse);
    }

    public Optional<UserResponse> findByLastname(String lastname) {
        return userRepo.findByLastname(lastname).map(userMapper::toResponse);
    }

    @Transactional(readOnly = false)
    public UserResponse save(UserRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new LogicBadRequestException("Пользователь с таким Email уже существует", "USER_ALREADY_EXISTS");
        }

        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepo.save(user));
    }

    @Transactional(readOnly = false)
    public UserResponse updateById(Long id, UserRequest updatedRequest) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID: " + id + " не найден"));


        if (!user.getEmail().equals(updatedRequest.getEmail()) && userRepo.existsByEmail(updatedRequest.getEmail())) {
            throw new LogicBadRequestException("Пользователь с таким Email уже существует", "USER_ALREADY_EXISTS");
        }

        userMapper.updateEntity(user, updatedRequest);
        return userMapper.toResponse(userRepo.save(user));
    }

    @Transactional(readOnly = false)
    public void deleteById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID: " + id + " не найден"));
        userRepo.delete(user);
    }
}