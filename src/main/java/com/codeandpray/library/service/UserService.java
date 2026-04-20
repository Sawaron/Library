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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;


    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userMapper.toResponseList(userRepo.findAll());
    }


    @Transactional(readOnly = true)
    public Page<UserResponse> findAllPaginated(Pageable pageable) {
        return userRepo.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findByNameContaining(String name, Pageable pageable) {
        return userRepo.findByFirstnameContaining(name, pageable)
                .map(userMapper::toResponse);
    }


    @Transactional
    public UserResponse save(UserRequest request) {
        User user = userMapper.toEntity(request);
        User savedUser = userRepo.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findByName(String name) {
        return userRepo.findByFirstname(name)
                .map(userMapper::toResponse);
    }

    @Transactional
    public Optional<UserResponse> updateById(Long id, UserRequest updatedRequest) {
        return userRepo.findById(id)
                .map(oldUser -> {
                    userMapper.updateEntity(oldUser, updatedRequest);
                    User savedUser = userRepo.save(oldUser);
                    return userMapper.toResponse(savedUser);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    userRepo.delete(user);
                    return true;
                })
                .orElse(false);
    }
}