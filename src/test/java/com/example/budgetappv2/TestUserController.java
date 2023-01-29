package com.example.budgetappv2;

import com.example.budgetappv2.user.User;
import com.example.budgetappv2.user.UserController;
import com.example.budgetappv2.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class TestUserController {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /*@Test
    @DisplayName("Should return all users")
    public void getAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));

    }*/

    @Test
    @DisplayName("Should get all users and return status 200 if found")
    public void getAllUsers_returnsFound() throws Exception {
        //given
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userService.getAllUsers()).thenReturn(new ResponseEntity<>(users, HttpStatus.OK));

        //when
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        ResponseEntity<List<User>> response = userController.getAllUsers();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    @DisplayName("Should get user by id and return status 200 if found")
    public void getUserById_returnsFound() throws Exception {
        //given
        User user = new User();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        //when
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        ResponseEntity<User> response = userController.getUserById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should post user and return status 201 if created")
    public void postUser_returnsCreated() throws Exception {
        //given
        User user = new User();
        user.setId(1L);
        when(userService.addUser(user)).thenReturn(new ResponseEntity<>(user, HttpStatus.CREATED));

        //when
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        ResponseEntity<User> response = userController.addUser(user);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should update user and return status 200 if updated")
    public void updateUser_returnsUpdated() throws Exception {
        //given
        User user = new User();
        user.setId(1L);
        when(userService.updateUser(user)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        //when
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        ResponseEntity<User> response = userController.updateUser(user);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should delete user and return status 200 if deleted")
    public void deleteUserById_returnsDeleted() throws Exception {
        //given
        User user = new User();
        user.setId(1L);
        when(userService.deleteUserById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        //when
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        ResponseEntity<HttpStatus> response = userController.deleteUser(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
