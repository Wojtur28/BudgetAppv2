package com.example.budgetappv2.group.mapper;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.group.dto.GroupNameDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GroupDtoMapper {

    public static ResponseEntity<Stream<GroupNameDto>>mapGroupToGroupDto(ResponseEntity<List<Group>> groups) {
        Stream<GroupNameDto> groupNameDtoStream = Objects.requireNonNull(groups.getBody())
                .stream()
                .map(group -> new GroupNameDto(group.getId(), group.getName()));
        return ResponseEntity.ok(groupNameDtoStream);
    }
}
