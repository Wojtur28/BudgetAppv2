package com.example.budgetappv2.user.dto;

import com.example.budgetappv2.group.dto.GroupReadDto;
import lombok.Builder;

import java.util.List;

@Builder
public record UserReadDto (long id, String username, String password, List<GroupReadDto> groups) {

}
