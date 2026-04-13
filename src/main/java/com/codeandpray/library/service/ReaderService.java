package com.codeandpray.library.service;

import com.codeandpray.library.entity.Reader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService{

    private List<Reader> readers = new ArrayList<>();



    public List<Reader> findAll() {
        return readers;
    }

    public Reader save(Reader reader) {
        readers.add(reader);
        return reader;
    }

    public Optional<Reader> findById(Long id) {
        return readers.stream().filter( reader -> reader.getId().equals(id)).findFirst();
    }

    public Optional<Reader> findByName(String name) {
        return readers.stream().filter( reader -> reader.getName().equals(name)).findFirst();
    }

    public Optional<Reader> updateById(Long id, Reader updatedReader) {
        return findById(id).map(oldUser -> {
            oldUser.setName(updatedReader.getName());
            oldUser.setEmail(updatedReader.getEmail());
            return save(oldUser);
        });
    }


}
