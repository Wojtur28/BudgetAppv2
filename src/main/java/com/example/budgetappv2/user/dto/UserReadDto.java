package com.example.budgetappv2.user.dto;

import com.example.budgetappv2.group.dto.GroupReadDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserReadDto {
    private long id;
    private String username;
    private String password;
    private List<GroupReadDto> groups;
}
