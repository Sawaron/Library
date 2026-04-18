package com.codeandpray.library.service;

import com.codeandpray.library.entity.User;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;



    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User save(User user) {
        userRepo.save(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepo.findByFirstname(name);
    }

    public Optional<User> updateById(Long id, User updatedUser) {
        return findById(id).map(oldUser -> {
            oldUser.setFirstname(updatedUser.getFirstname());
            oldUser.setLastname(updatedUser.getLastname());
            oldUser.setEmail(updatedUser.getEmail());
            oldUser.setPhone(updatedUser.getPhone());
            oldUser.setPassword(updatedUser.getPassword());
            return userRepo.save(oldUser);
        });
    }


}
