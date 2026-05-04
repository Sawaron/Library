package com.codeandpray.library.service;

import com.codeandpray.library.dto.CreateEditionRequest;
import com.codeandpray.library.dto.UpdateEditionRequest;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.catalog.Edition;
import com.codeandpray.library.exception.entity.EditionNotFoundException;
import com.codeandpray.library.mapper.EditionMapper;
import com.codeandpray.library.repo.EditionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditionService {

    private final EditionRepo editionRepo;
    private final BookService bookService;
    private final EditionMapper editionMapper;

    @Transactional(readOnly = true)
    public Page<Edition> getAll(int page, int size) {
        return editionRepo.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public Edition getById(Long id) {
        return editionRepo.findById(id)
                .orElseThrow(() -> new EditionNotFoundException("Версия с таким ID: "+ id + " не найдена"));
    }

    @Transactional
    public Edition create(CreateEditionRequest dto) {
        Book book = bookService.getById(dto.getBookId());
        Edition edition = editionMapper.toEntity(dto, book);
        return editionRepo.save(edition);
    }

    @Transactional
    public Edition update(Long id, UpdateEditionRequest dto) {
        Edition edition = getById(id);
        editionMapper.updateEntity(edition, dto);
        return editionRepo.save(edition);
    }

    @Transactional
    public void delete(Long id) {
        editionRepo.delete(getById(id));
    }
}