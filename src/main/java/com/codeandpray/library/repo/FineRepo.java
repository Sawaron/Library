package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Наследуемся от JpaRepository, чтобы появились методы save, findAll, findById и т.д.
public interface FineRepo extends JpaRepository<Fine, Long> {
}