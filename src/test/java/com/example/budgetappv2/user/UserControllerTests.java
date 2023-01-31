package com.example.budgetappv2.user;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private final List<User> users = new ArrayList<>(Arrays.asList(
            User.builder()
                    .id(1L)
                    .username("Wojtur")
                    .password("zaq1@WSX")
                    .build(),
            User.builder()
                    .id(2L)
                    .username("TEST")
                    .password("1234")
                    .build()
    ));

    @Test
    @DisplayName("Should get all users and return status 200")
    public void should_get_all_users() throws Exception {

        when(userService.getAllUsers()).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));

        mockMvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].username").value("Wojtur"))
                    .andExpect(jsonPath("$[0].password").value("zaq1@WSX"))
                    .andExpect(jsonPath("$[1].username").value("TEST"))
                    .andExpect(jsonPath("$[1].password").value("1234"))
                    .andDo(print());

        ResponseEntity<List<User>> response = userController.getAllUsers();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users, response.getBody());
    }

    @Test
    @DisplayName("Should get user by id and return status 200")
    public void should_get_user_by_id() throws Exception {

        when(userService.getUserById(1L)).thenReturn(new ResponseEntity<>(users.get(0), HttpStatus.OK));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Wojtur"))
                .andExpect(jsonPath("$.password").value("zaq1@WSX"))
                .andDo(print());

        ResponseEntity<User> response = userController.getUserById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should get user by wrong id and return status 404")
    public void should_get_user_by_wrong_id() throws Exception {

        when(userService.getUserById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/users/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<User> response = userController.getUserById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should add user and return status 201")
    public void should_post_user() throws Exception {

        when(userService.addUser(any(User.class))).thenReturn(new ResponseEntity<>(users.get(0), HttpStatus.CREATED));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"Wojtur\",\n" +
                        "    \"password\": \"zaq1@WSX\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Wojtur"))
                .andExpect(jsonPath("$.password").value("zaq1@WSX"))
                .andDo(print());

        ResponseEntity<User> response = userController.addUser(users.get(0));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(users.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should add user with wrong data and return status 400")
    public void should_post_user_with_wrong_data() throws Exception {

        when(userService.addUser(any(User.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"Wojtur\",\n" +
                        "    \"password\": \"zaq1@WSX\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<User> response = userController.addUser(users.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should update user and return status 200")
    public void should_update_user() throws Exception {

        when(userService.updateUser(any(User.class))).thenReturn(new ResponseEntity<>(users.get(0), HttpStatus.OK));

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"Wojtur\",\n" +
                        "    \"password\": \"zaq1@WSX\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Wojtur"))
                .andExpect(jsonPath("$.password").value("zaq1@WSX"))
                .andDo(print());

        ResponseEntity<User> response = userController.updateUser(users.get(0));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should update user with wrong data and return status 400")
    public void should_update_user_with_wrong_data() throws Exception {

        when(userService.updateUser(any(User.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"Wojtur\",\n" +
                        "    \"password\": \"zaq1@WSX\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<User> response = userController.updateUser(users.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete user by id and return status 200")
    public void should_delete_user_by_id() throws Exception {

        when(userService.deleteUserById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andDo(print());

        ResponseEntity<HttpStatus> response = userController.deleteUserById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete user by wrong id and return status 404")
    public void should_delete_user_by_wrong_id() throws Exception {

        when(userService.deleteUserById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/users/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<HttpStatus> response = userController.deleteUserById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}
