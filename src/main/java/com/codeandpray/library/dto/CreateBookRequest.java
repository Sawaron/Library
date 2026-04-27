package com.codeandpray.library.dto;

import com.codeandpray.library.enums.AgeCategory;
import lombok.Data;

import java.util.Set;

@Data
public class CreateBookRequest {
    private String title;
    private Set<Long> authorIds;
    private Set<Long> genreIds;
    private String isbn;
    private String description;
    private String language;
    private AgeCategory ageCategory;
    private boolean hasAudiobook;
    private int pageCount;
    private int count;


}
