package com.example.budgetappv2.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable long id) {
        return groupService.getGroupById(id);
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
