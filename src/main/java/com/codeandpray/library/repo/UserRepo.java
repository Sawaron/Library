package com.codeandpray.library.repo;

import com.codeandpray.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findByFirstname(String name);

}
