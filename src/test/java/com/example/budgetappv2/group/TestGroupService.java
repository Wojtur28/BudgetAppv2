package com.example.budgetappv2.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class TestGroupService {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    @DisplayName("Should get all groups and return status 200")
    public void getAllGroups_returnsFound() throws Exception {
        //given
        List<Group> groups = new ArrayList<>();
        groups.add(new Group());
        when(groupService.getAllGroups()).thenReturn(new ResponseEntity<>(groups, HttpStatus.OK));
        //when
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<List<Group>> response = groupController.getAllGroups();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should get group by id and return status 200 if found")
    public void getGroupById_returnsFound() throws Exception {
        //given
        Group group = new Group();
        group.setId(1L);
        when(groupService.getGroupById(1L)).thenReturn(new ResponseEntity<>(group, HttpStatus.OK));
        //when
        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Group> response = groupController.getGroupById(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should add group and return status 200 if created")
    public void addGroup_returnsCreated() throws Exception {
        //given
        Group group = new Group();
        group.setId(1L);
        when(groupService.addGroup(group)).thenReturn(new ResponseEntity<>(group, HttpStatus.CREATED));
        //when
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Group> response = groupController.addGroup(group);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should update group and return status 200 if updated")
    public void updateGroup_returnsUpdated() throws Exception {
        //given
        Group group = new Group();
        group.setId(1L);
        when(groupService.updateGroup(group)).thenReturn(new ResponseEntity<>(group, HttpStatus.OK));
        //when
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Group> response = groupController.updateGroup(group);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should delete group by id and return status 200 if deleted")
    public void deleteGroupById_returnsDeleted() throws Exception {
        //given
        Group group = new Group();
        group.setId(1L);
        when(groupService.deleteGroupById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        //when
        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<HttpStatus> response = groupController.deleteGroupById(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
