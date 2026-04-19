package com.codeandpray.library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String firstname;
    private String lastname;

    private String email;
    private String phone;

    private LocalDate registrationDate;

    private String role;
}
