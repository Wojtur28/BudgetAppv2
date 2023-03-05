package com.example.budgetappv2.service;

import com.example.budgetappv2.user.Role;
import com.example.budgetappv2.user.User;
import com.example.budgetappv2.user.UserRepository;
import com.example.budgetappv2.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("test")
                .password("test")
                .role(Role.USER)
                .build();

        users = List.of(user);
    }

    @Test
    @DisplayName("Should return all users")
    public void shouldReturnAllUsers() {

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> foundUsers = userService.getUsers().getBody();

        assertEquals(foundUsers, users);
    }

    @Test
    @DisplayName("Should return user by id")
    public void shouldReturnUserById() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> foundUser = userService.getUserById(1L);

        assertEquals(foundUser.getBody(), user);
    }

    @Test
    @DisplayName("Should create user")
    public void shouldCreateUser() {

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> createdUser = userService.addUser(user);

        assertEquals(createdUser.getBody(), user);
    }

    @Test
    @DisplayName("Should update user")
    public void shouldUpdateUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> updatedUser = userService.updateUser(user);

        assertEquals(updatedUser.getBody(), user);
    }

    @Test
    @DisplayName("Should delete user")
    public void shouldDeleteUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<HttpStatus> deletedUser = userService.deleteUserById(1L);

        assertEquals(HttpStatus.NO_CONTENT, deletedUser.getStatusCode());
    }
}
