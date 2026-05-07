package com.codeandpray.library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String firstname;
    private String lastname;

    private LocalDate birthDate;

    private String password;

    private String phone;
    private String email;
}