package com.example.budgetappv2.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class AuthResponse {

    private String username;
    private String accessToken;

}
