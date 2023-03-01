package com.example.budgetappv2.user.dto;

import com.example.budgetappv2.user.Role;

public record UserDto(String username, String password, Role role) {
}
