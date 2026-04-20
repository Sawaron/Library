package com.codeandpray.library.mapper;

import com.codeandpray.library.dto.CreateEditionRequest;
import com.codeandpray.library.dto.EditionResponse;
import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.Edition;
import org.springframework.data.domain.Page;

public class EditionMapper {

    public static Edition toEntity(CreateEditionRequest dto, Book book) {
        return Edition.builder()
                .book(book)
                .editionNumber(dto.getEditionNumber())
                .publisher(dto.getPublisher())
                .build();
    }

    public static EditionResponse toResponse(Edition entity) {
        return EditionResponse.builder()
                .id(entity.getId())
                .bookId(entity.getBook().getId())
                .editionNumber(entity.getEditionNumber())
                .publisher(entity.getPublisher())
                .publishDate(entity.getPublishDate() != null ? entity.getPublishDate().toString() : null)
                .build();
    }

    public static PageResponse<EditionResponse> toPageResponse(Page<Edition> page) {
        return PageResponse.of(page.map(EditionMapper::toResponse));
    }
}
