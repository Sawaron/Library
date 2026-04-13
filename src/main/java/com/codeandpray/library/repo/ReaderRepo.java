package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ReaderRepo extends JpaRepository<Reader, Long> {
    public List<Reader> getAllReaders();
    public Reader createReader(Reader reader);

}
