package com.example.coffeeshopmanagementsystem.security.service.facade;

import com.example.coffeeshopmanagementsystem.security.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto findRoleById(Long id);

    RoleDto findRoleByName(String name);

    RoleDto saveRole(RoleDto roleDto);

    List<RoleDto> saveAll(List<RoleDto> roleDtos);

    RoleDto updateRole(Long id, RoleDto roleDto);

    List<RoleDto> updateAll(List<RoleDto> roleDtoList);

    void deleteRole(Long id);
}
