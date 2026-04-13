package com.codeandpray.library.repo;

import com.codeandpray.library.entity.Reader;


import java.util.List;

public interface ReaderRepo {
    public List<Reader> getAllReaders();
    public Reader createReader(Reader reader);

}
