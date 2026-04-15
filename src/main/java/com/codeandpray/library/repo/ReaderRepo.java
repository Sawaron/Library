package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ReaderRepo extends JpaRepository<Reader, Long> {

    public Optional<Reader> findByFirstname(String name);

}
