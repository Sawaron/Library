package com.codeandpray.library.service;

import com.codeandpray.library.dto.UserRequest;
import com.codeandpray.library.dto.UserResponse;
import com.codeandpray.library.entity.User;
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
@Transactional(readOnly = true)  // ← По умолчанию readOnly для всех методов поиска
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());  // ← Заменили toResponseList на stream
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

    public Optional<UserResponse> findById(Long id) {
        return userRepo.findById(id).map(userMapper::toResponse);
    }

    public Optional<UserResponse> findByName(String name) {
        return userRepo.findByFirstname(name).map(userMapper::toResponse);
    }

    public Optional<UserResponse> findByLastname(String lastname) {
        return userRepo.findByLastname(lastname).map(userMapper::toResponse);  // ← Новый метод
    }

    @Transactional(readOnly = false)  // ← Переопределяем для записи
    public UserResponse save(UserRequest request) {
        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepo.save(user));
    }

    @Transactional(readOnly = false)
    public Optional<UserResponse> updateById(Long id, UserRequest updatedRequest) {
        return userRepo.findById(id)
                .map(oldUser -> {
                    userMapper.updateEntity(oldUser, updatedRequest);
                    return userMapper.toResponse(userRepo.save(oldUser));
                });
    }

    @Transactional(readOnly = false)
    public boolean deleteById(Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    userRepo.delete(user);
                    return true;
                })
                .orElse(false);
    }
}