package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditionRepo extends JpaRepository<Edition, Long> {
}