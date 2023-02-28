package com.example.budgetappv2.controller;

import com.example.budgetappv2.user.UserController;
import com.example.budgetappv2.user.dto.UserReadDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;



    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getAnyAdmin() throws Exception {

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .param("id", String.valueOf(3752))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

        System.out.println(result);

        assertTrue(result.contains("admin"));
    }

    @Test
    public void getUsers_returnsHttpStatusOk() {
        ResponseEntity<Stream<UserReadDto>> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
