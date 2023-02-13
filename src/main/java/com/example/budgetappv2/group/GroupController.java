package com.example.budgetappv2.group;

import com.example.budgetappv2.group.dto.GroupDto;
import com.example.budgetappv2.group.dto.GroupReadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static com.example.budgetappv2.group.mapper.GroupMapper.mapToGroup;
import static com.example.budgetappv2.group.mapper.GroupReadDtoMapper.mapGroupToGroupReadDto;


@RestController
@RequestMapping("/groups")
public class GroupController {

    private static final long EMPTY_ID = -1;
    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity<Stream<GroupReadDto>> getGroups() {
        return mapGroupToGroupReadDto(groupService.getGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable long id) {
        return groupService.getGroupById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Group> getGroupByName(@PathVariable String name) {
        return groupService.getGroupByName(name);
    }
    //TODO: Change this method to use a DTO
    @PostMapping
    public ResponseEntity<Group> addGroup(@RequestBody GroupDto groupDto) {
        return groupService.addGroup(mapToGroup(EMPTY_ID, groupDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable long id, @RequestBody GroupDto groupDto) {
        return groupService.updateGroup(mapToGroup(id, groupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupById(@PathVariable long id) {
        return groupService.deleteGroupById(id);
    }
}
