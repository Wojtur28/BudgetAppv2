package com.example.budgetappv2.user.mapper;

import com.example.budgetappv2.user.User;
import com.example.budgetappv2.user.dto.UserUsernameDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class UserDtoMapper {

    public static ResponseEntity<Stream<UserUsernameDto>> mapUserToUserUsernameDto(ResponseEntity<List<User>> users) {
        Stream<UserUsernameDto> userUsernameDtoStream = Objects.requireNonNull(users.getBody()).stream()
                .map(user -> new UserUsernameDto(user.getUsername()));
        return ResponseEntity.ok(userUsernameDtoStream);
    }
}
