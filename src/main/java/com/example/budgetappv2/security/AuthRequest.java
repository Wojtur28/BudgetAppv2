package com.example.budgetappv2.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuthRequest {
    String username;
    String password;
}
