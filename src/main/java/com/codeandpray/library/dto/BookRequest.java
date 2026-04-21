package com.codeandpray.library.dto;

import com.codeandpray.library.enums.AgeCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Название книги не может быть пустым")
    private String title;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @Min(value = 1, message = "В книге должна быть минимум 1 страница")
    private int pageCount;

    @NotBlank(message = "Язык обязателен для заполнения")
    private String language;

    @PositiveOrZero(message = "Цена не может быть отрицательной")
    private float price;

    private boolean hasAudiobook;

    private Integer readerTime;

    private AgeCategory ageCategory;

    @NotBlank(message = "ISBN обязателен")
    private String isbn;

    @PositiveOrZero(message = "Количество не может быть отрицательной")
    private int count;

    @NotEmpty(message = "У книги должен быть хотя бы один автор")
    private Set<Long> authorIds;

    @NotEmpty(message = "У книги должен быть хотя бы один жанр")
    private Set<Long> genreIds;
}