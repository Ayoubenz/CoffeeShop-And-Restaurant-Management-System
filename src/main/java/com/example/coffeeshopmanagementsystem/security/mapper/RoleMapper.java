package com.example.coffeeshopmanagementsystem.security.mapper;

import com.example.coffeeshopmanagementsystem.security.dto.RoleDto;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);

}
