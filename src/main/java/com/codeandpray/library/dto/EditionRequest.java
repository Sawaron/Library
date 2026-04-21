package com.codeandpray.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditionRequest {

    @NotNull(message = "ID книги обязателен")
    private Long bookId;

    @NotNull(message = "Номер издания обязателен")
    @Positive(message = "Номер издания должен быть положительным числом")
    private Integer editionNumber;

    @NotBlank(message = "Издатель не может быть пустым")
    private String publisher;

}