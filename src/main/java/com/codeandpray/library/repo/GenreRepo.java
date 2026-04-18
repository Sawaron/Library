package com.codeandpray.library.repo;

import com.codeandpray.library.catalog.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Long> {
}