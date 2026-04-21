package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.CreateEditionRequest;
import com.codeandpray.library.dto.EditionResponse;
import com.codeandpray.library.dto.PageResponse;
import com.codeandpray.library.dto.UpdateEditionRequest;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.catalog.Edition;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EditionMapper {

    public Edition toEntity(CreateEditionRequest dto, Book book) {
        return Edition.builder()
                .book(book)
                .editionNumber(dto.getEditionNumber())
                .publisher(dto.getPublisher())
                .build();
    }

    public void updateEntity(Edition edition, UpdateEditionRequest dto) {
        if (dto.getEditionNumber() != null) {
            edition.setEditionNumber(dto.getEditionNumber());
        }
        if (dto.getPublisher() != null) {
            edition.setPublisher(dto.getPublisher());
        }
    }

    public EditionResponse toResponse(Edition entity) {
        return EditionResponse.builder()
                .id(entity.getId())
                .bookId(entity.getBook().getId())
                .editionNumber(entity.getEditionNumber())
                .publisher(entity.getPublisher())
                .publishDate(entity.getPublishDate() != null ? entity.getPublishDate().toString() : null)
                .build();
    }

    public  PageResponse<EditionResponse> toPageResponse(Page<Edition> page) {
        return PageResponse.of(page.map(this::toResponse));
    }
}
