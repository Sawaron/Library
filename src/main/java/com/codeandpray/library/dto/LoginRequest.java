package com.codeandpray.library.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String firstname;
    private String password;
}