package com.example.budgetappv2.group.mapper;

import com.example.budgetappv2.group.Group;
import com.example.budgetappv2.group.dto.GroupDto;

public class GroupMapper {

    public static Group mapToGroup(long id, GroupDto groupDto) {
        return Group.builder()
                .id(id)
                .name(groupDto.name())
                .build();
    }
}
