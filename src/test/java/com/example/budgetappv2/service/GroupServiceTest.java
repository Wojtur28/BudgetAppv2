package com.example.budgetappv2.service;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.group.GroupRepository;
import com.example.budgetappv2.group.GroupService;
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
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    private Group group;

    private List<Group> groups;

    @BeforeEach
    public void setUp() {
        group = Group.builder()
                .id(1L)
                .name("test")
                .build();

        groups = List.of(group);
    }

    @Test
    @DisplayName("Should return all groups")
    public void shouldReturnAllGroups() {

        when(groupRepository.findAll()).thenReturn(List.of(group));

        List<Group> foundGroups = groupService.getGroups().getBody();

        assertEquals(groups, foundGroups);
    }

    @Test
    @DisplayName("Should return group by id")
    public void shouldReturnGroupById() {

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Group foundGroup = groupService.getGroupById(1L).getBody();

        assertEquals(group, foundGroup);
    }

    @Test
    @DisplayName("Should create group")
    public void shouldCreateGroup() {

        when(groupRepository.save(group)).thenReturn(group);

        Group createdGroup = groupService.addGroup(group).getBody();

        assertEquals(group, createdGroup);
    }

    @Test
    @DisplayName("Should update group")
    public void shouldUpdateGroup() {

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);

        Group updatedGroup = groupService.updateGroup(group).getBody();

        assertEquals(group, updatedGroup);
    }

    @Test
    @DisplayName("Should delete group")
    public void shouldDeleteGroup() {

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        ResponseEntity<HttpStatus> response = groupService.deleteGroupById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
