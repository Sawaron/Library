package com.codeandpray.library.repo;

import com.codeandpray.library.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByFirstname(String name);

    Page<User> findAll(Pageable pageable);

    Page<User> findByFirstnameContaining(String name, Pageable pageable);

    Page<User> findByLastnameContaining(String lastname, Pageable pageable);

    Page<User> findByRole(String role, Pageable pageable);
}