package com.codeandpray.library.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String firstname;
    private String lastname;

    private String password;

    private String phone;
    private String email;



}
