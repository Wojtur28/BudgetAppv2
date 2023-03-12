package com.example.budgetappv2.controller;

import com.example.budgetappv2.user.Role;
import com.example.budgetappv2.user.User;
import com.example.budgetappv2.user.UserController;
import com.example.budgetappv2.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private ResponseEntity<User> user1;
    private ResponseEntity<List<User>> users;

    @BeforeEach
    public void setUp() {
        user1 = ResponseEntity.ok(User.builder()
                .id(1L)
                .username("test1")
                .password("test1")
                .role(Role.ADMIN)
                .groups(List.of())
                .build());

        ResponseEntity<User> user2 = ResponseEntity.ok(User.builder()
                .id(2L)
                .username("test2")
                .password("test2")
                .role(Role.USER)
                .groups(List.of())
                .build());

        users = ResponseEntity.ok(List.of(Objects.requireNonNull(user1.getBody()), Objects.requireNonNull(user2.getBody())));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return all users")
    public void shouldReturnAllUsers() throws Exception {
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> hasSize(2))
                .andExpect(result -> jsonPath("$[0].id", is(1)))
                .andExpect(result -> jsonPath("$[0].username", is("test1")))
                .andExpect(result -> jsonPath("$[0].password", is("test1")))
                .andExpect(result -> jsonPath("$[1].id", is(2)))
                .andExpect(result -> jsonPath("$[1].username", is("test2")))
                .andExpect(result -> jsonPath("$[1].password", is("test2")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return user by id")
    public void shouldReturnUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1)))
                .andExpect(result -> jsonPath("$.username", is("test1")))
                .andExpect(result -> jsonPath("$.password", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should create user")
    public void shouldCreateUser() throws Exception {
        when(userService.addUser(user1.getBody())).thenReturn(user1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "test1",
                                    "password": "test1",
                                    "role": "ADMIN"
                                    }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.username", is("test1")))
                .andExpect(result -> jsonPath("$.password", is("test1")));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should update user")
    public void shouldUpdateUser() throws Exception {
        when(userService.updateUser(user1.getBody())).thenReturn(user1);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "test1",
                                    "password": "test1",
                                    "role": "ADMIN"
                                    }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.username", is("test1")))
                .andExpect(result -> jsonPath("$.password", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should delete user by id")
    public void shouldDeleteUserById() throws Exception {
        when(userService.deleteUserById(1L)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
