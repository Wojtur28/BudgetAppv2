package com.example.budgetappv2.group.mapper;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.group.dto.GroupDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GroupDtoMapper {

    public static ResponseEntity<Stream<GroupDto>>mapGroupToGroupDto(ResponseEntity<List<Group>> groups) {
        Stream<GroupDto> groupNameDtoStream = Objects.requireNonNull(groups.getBody())
                .stream()
                .map(group -> new GroupDto(group.getName()));
        return ResponseEntity.ok(groupNameDtoStream);
    }
}
