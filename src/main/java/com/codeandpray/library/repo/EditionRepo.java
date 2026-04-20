package com.codeandpray.library.repo;

import com.codeandpray.library.catalog.Edition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionRepo extends JpaRepository<Edition, Long> {
}