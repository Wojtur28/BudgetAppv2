package com.example.budgetappv2.group;

import com.example.budgetappv2.group.dto.GroupNameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static com.example.budgetappv2.group.mapper.GroupDtoMapper.mapGroupToGroupDto;


@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return groupService.getAllGroups();
    }
    // TODO: Change GetAllGroups to getGroups change name rest too

    @GetMapping("/name")
    public ResponseEntity<Stream<GroupNameDto>> getGroupsNames() {
        return mapGroupToGroupDto(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable long id) {
        return groupService.getGroupById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Group> getGroupByName(@PathVariable String name) {
        return groupService.getGroupByName(name);
    }

    @PostMapping
    public ResponseEntity<Group> addGroup(@RequestBody Group group) {
        return groupService.addGroup(group);
    }

    @PutMapping
    public ResponseEntity<Group> updateGroup(@RequestBody Group group) {
        return groupService.updateGroup(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupById(@PathVariable long id) {
        return groupService.deleteGroupById(id);
    }
}
