package com.example.budgetappv2.group;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public ResponseEntity<List<Group>> getGroups(){
        try{
            return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getAllGroups\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Group> getGroupById(Long id) {
        try{
            return new ResponseEntity<>(groupRepository.findById(id).stream().findFirst()
                    .orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getGroupById\"");
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Group> getGroupByName(String name) {
        try{
            return new ResponseEntity<>(groupRepository.findByName(name).stream().findFirst()
                    .orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getGroupByName\"");
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Group> addGroup(Group group) {
        try {
            List<Group> allGroups = groupRepository.findAll();
            for (Group g : allGroups) {
                if (g.getName().equals(group.getName())) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(groupRepository.save(group), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("createGroup exception: "+ group.toString()+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Group> updateGroup(Group group){
        try {
            Group _group = groupRepository.findById(group.getId()).stream()
                    .findFirst().orElse(null);
            if (_group == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            _group.setName(group.getName());
            return new ResponseEntity<>(groupRepository.save(_group), HttpStatus.OK);
        } catch (Exception e) {
            log.info("updateGroup exception: "+ group.toString()+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> deleteGroupById(Long id) {
        try {
            groupRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("deleteGroup exception: "+ id.toString()+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
