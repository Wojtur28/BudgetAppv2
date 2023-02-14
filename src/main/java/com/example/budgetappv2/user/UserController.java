package com.example.budgetappv2.user;

import com.example.budgetappv2.user.dto.UserDto;
import com.example.budgetappv2.user.dto.UserReadDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static com.example.budgetappv2.user.mapper.UserMapper.mapToUser;
import static com.example.budgetappv2.user.mapper.UserReadDtoMapper.mapUserToUserReadDto;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private static final long EMPTY_ID = -1;
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<Stream<UserReadDto>> getUsers() {
        return mapUserToUserReadDto(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(mapToUser(EMPTY_ID, userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        return userService.updateUser(mapToUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

}
