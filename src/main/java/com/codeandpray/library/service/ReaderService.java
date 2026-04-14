package com.codeandpray.library.service;

import com.codeandpray.library.entity.Reader;
import com.codeandpray.library.repo.ReaderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService{

    private final ReaderRepo readerRepo;



    public List<Reader> findAll() {
        return readerRepo.findAll();
    }

    public Reader save(Reader reader) {
        readerRepo.save(reader);
        return reader;
    }

    public Optional<Reader> findById(Long id) {
        return readerRepo.findById(id);
    }

    public Optional<Reader> findByName(String name) {
        return readerRepo.findByName(name);
    }

    public Optional<Reader> updateById(Long id, Reader updatedReader) {
        return findById(id).map(oldReader -> {
            oldReader.setFirstname(updatedReader.getFirstname());
            oldReader.setLastname(updatedReader.getLastname());
            oldReader.setAddress(updatedReader.getAddress());
            oldReader.setPhone(updatedReader.getPhone());
            oldReader.setRegistrationDate(updatedReader.getRegistrationDate());
            oldReader.setStatus(updatedReader.getStatus());
            oldReader.setEmail(updatedReader.getEmail());
            return readerRepo.save(oldReader);
        });
    }


}
