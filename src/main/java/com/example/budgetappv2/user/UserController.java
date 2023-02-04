package com.example.budgetappv2.user;

import com.example.budgetappv2.user.dto.UserUsernameDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static com.example.budgetappv2.user.mapper.UserDtoMapper.mapUserToUserUsernameDto;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

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

    /*@GetMapping("/{name}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String name) {
        return userService.getUserByUsername(name);
    }*/

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

}
