package com.example.budgetappv2.group;

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
public class GroupControllerTests {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    private final List<Group> groups = new ArrayList<>(Arrays.asList(
            Group.builder()
                    .id(1L)
                    .name("Budzet Rodzinny")
                    .build(),
            Group.builder()
                    .id(2L)
                    .name("TEST")
                    .build()
    ));

    @Test
    @DisplayName("Should get all groups and return status 200")
    public void should_get_all_groups() throws Exception {

        when(groupService.getAllGroups()).thenReturn(new ResponseEntity<>(groups, HttpStatus.OK));

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Budzet Rodzinny"))
                .andExpect(jsonPath("$[1].name").value("TEST"))
                .andDo(print());

        ResponseEntity<List<Group>> response = groupService.getAllGroups();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(groups, response.getBody());
    }

    @Test
    @DisplayName("Should get group by id and return status 200")
    public void should_get_group_by_id() throws Exception {

        when(groupService.getGroupById(1L)).thenReturn(new ResponseEntity<>(groups.get(0), HttpStatus.OK));

        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Budzet Rodzinny"))
                .andDo(print());

        ResponseEntity<Group> response = groupService.getGroupById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(groups.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should get group by wrong id and return status 404")
    public void should_get_group_by_wrong_id() throws Exception {

        when(groupService.getGroupById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/groups/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<Group> response = groupService.getGroupById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should add group and return status 201")
    public void should_post_group() throws Exception {

        when(groupService.addGroup(any(Group.class))).thenReturn(new ResponseEntity<>(groups.get(0) ,HttpStatus.CREATED));

        mockMvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Budzet Rodzinny\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Budzet Rodzinny"))
                .andDo(print());

        ResponseEntity<Group> response = groupService.addGroup(groups.get(0));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(groups.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should add group with wrong data and return status 400")
    public void should_post_group_with_wrong_data() throws Exception {

        when(groupService.addGroup(any(Group.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"\",\n" +
                        "    \"date\": \"\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<Group> response = groupService.addGroup(groups.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should put group and return status 200")
    public void should_put_group() throws Exception {

        when(groupService.updateGroup(any(Group.class))).thenReturn(new ResponseEntity<>(groups.get(0), HttpStatus.OK));

        mockMvc.perform(put("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Budzet Rodzinny\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Budzet Rodzinny"))
                .andDo(print());

        ResponseEntity<Group> response = groupService.updateGroup(groups.get(0));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(groups.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should update group with wrong data and return status 400")
    public void should_put_group_with_wrong_data() throws Exception {

        when(groupService.updateGroup(any(Group.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(put("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"\",\n" +
                        "    \"date\": \"\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<Group> response = groupService.updateGroup(groups.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete group by id and return status 200")
    public void should_delete_group_by_id() throws Exception {

        when(groupService.deleteGroupById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(delete("/groups/1"))
                .andExpect(status().isOk())
                .andDo(print());

        ResponseEntity<HttpStatus> response = groupService.deleteGroupById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete group by wrong id and return status 404")
    public void should_delete_group_by_wrong_id() throws Exception {

        when(groupService.deleteGroupById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/groups/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<HttpStatus> response = groupService.deleteGroupById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
