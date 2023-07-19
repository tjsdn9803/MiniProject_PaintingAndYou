package com.example.cicdtest.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;

    private String password;

    private String email;

    private boolean admin = false;

    private String adminToken = "";
}
