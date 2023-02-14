package com.example.budgetappv2.user.mapper;

import com.example.budgetappv2.user.User;
import com.example.budgetappv2.user.dto.UserDto;

public class UserMapper {

    public static User mapToUser(long id, UserDto userDto) {
        return User.builder()
                .id(id)
                .username(userDto.username())
                .password(userDto.password())
                .build();
    }
}
