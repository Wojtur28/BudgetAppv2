package com.example.budgetappv2.controller;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.group.GroupController;
import com.example.budgetappv2.group.GroupService;
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

@WebMvcTest(controllers = GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GroupControllerTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    private ResponseEntity<Group> group1;

    private ResponseEntity<List<Group>> groups;

    @BeforeEach
    public void setUp() {
        group1 = ResponseEntity.ok(Group.builder()
                .id(1L)
                .name("test1")
                .categories(List.of())
                .build());

        ResponseEntity<Group> group2 = ResponseEntity.ok(Group.builder()
                .id(2L)
                .name("test2")
                .categories(List.of())
                .build());

        groups = ResponseEntity.ok(List.of(Objects.requireNonNull(group1.getBody()), Objects.requireNonNull(group2.getBody())));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return all groups")
    public void shouldReturnAllGroups() throws Exception {
        when(groupService.getGroups()).thenReturn(groups);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> hasSize(2))
                .andExpect(result -> jsonPath("$[0].id", is(1)))
                .andExpect(result -> jsonPath("$[0].name", is("test1")))
                .andExpect(result -> jsonPath("$[1].id", is(2)))
                .andExpect(result -> jsonPath("$[1].name", is("test2")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return group by id")
    public void shouldReturnGroupById() throws Exception {
        when(groupService.getGroupById(1L)).thenReturn(group1);

        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1)))
                .andExpect(result -> jsonPath("$.name", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should create group")
    public void shouldCreateGroup() throws Exception {
        when(groupService.addGroup(group1.getBody())).thenReturn(group1);

        mockMvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"test1\"}"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1)))
                .andExpect(result -> jsonPath("$.name", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should update group")
    public void shouldUpdateGroup() throws Exception {
        when(groupService.updateGroup(group1.getBody())).thenReturn(group1);

        mockMvc.perform(put("/groups/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "test1"
                        }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.name", is("test1")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should delete group by id")
    public void shouldDeleteGroupById() throws Exception {
        when(groupService.deleteGroupById(1L)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/groups/1"))
                .andExpect(status().isOk());
    }
}
