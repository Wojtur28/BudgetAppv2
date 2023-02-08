package com.example.budgetappv2.user;

import com.example.budgetappv2.user.dto.UserDto;
import com.example.budgetappv2.user.dto.UserUsernameDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static com.example.budgetappv2.user.mapper.UserDtoMapper.mapUserToUserUsernameDto;
import static com.example.budgetappv2.user.mapper.UserMapper.mapToUser;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private static final long EMPTY_ID = -1;
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/username")
    public ResponseEntity<Stream<UserUsernameDto>> getAllUsersUsernames() {
        return mapUserToUserUsernameDto(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{name}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String name) {
        return userService.getUserByUsername(name);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(mapToUser(EMPTY_ID, userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        return userService.updateUser(mapToUser(id,userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

}
