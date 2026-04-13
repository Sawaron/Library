package com.codeandpray.library.service;

import com.codeandpray.library.entity.Reader;
import com.codeandpray.library.repo.ReaderRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderService implements ReaderRepo{

    private List<Reader> readers = new ArrayList<>();



    public List<Reader> getAllReaders() {
        return readers;
    }

    @Override
    public Reader createReader(Reader reader) {
        readers.add(reader);
        return reader;
    }


}
