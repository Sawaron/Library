package com.codeandpray.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private LocalDate registrationDate;

    private String role;
}
