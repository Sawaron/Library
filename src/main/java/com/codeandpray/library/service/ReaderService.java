package com.codeandpray.library.service;

import com.codeandpray.library.entity.Reader;
import com.codeandpray.library.repo.ReaderRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService{

    private List<Reader> readers = new ArrayList<>();



    public List<Reader> getAllReaders() {
        return readers;
    }

    public Reader createReader(Reader reader) {
        readers.add(reader);
        return reader;
    }

    public Optional<Reader> getReaderById(Long id) {
        return readers.stream().filter( reader -> reader.getId().equals(id)).findFirst();
    }

    public Optional<Reader> getReaderByName(String name) {
        return readers.stream().filter( reader -> reader.getName().equals(name)).findFirst();
    }

    public Optional<Reader> updateReader(Long id, Reader updatedReader) {
        return getReaderById(id).map( oldUser -> {
            oldUser.setName(updatedReader.getName());
            oldUser.setEmail(updatedReader.getEmail());
            return oldUser;
        });
    }


}
