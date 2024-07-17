package com.example.coffeeshopmanagementsystem.security.service.impl;

import com.example.coffeeshopmanagementsystem.security.dto.RoleDto;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.mapper.RoleMapper;
import com.example.coffeeshopmanagementsystem.security.repository.RoleRepository;
import com.example.coffeeshopmanagementsystem.security.service.facade.RoleService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    @Override
    public RoleDto findRoleById(Long id )
    {
        Role role  = roleRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a role with the given id"));
        return roleMapper.toDto(role);
    }
    @Override
    public RoleDto findRoleByName(String name)
    {
        Role role = roleRepository
                .findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a role with he given name"));
        return roleMapper.toDto(role);
    }
    @Override
    public RoleDto saveRole(RoleDto roleDto)
    {
        try {
            Role savedRole = roleRepository.save(roleMapper.toEntity(roleDto));
            return roleMapper.toDto(savedRole);
        }catch (Exception e)
        {
            throw new RuntimeException("Error saving the role", e);
        }
    }
    @Override
    public List<RoleDto> saveAll(List<RoleDto> roleDtos)
    {
        return roleDtos.stream().map(this::saveRole).toList();
    }

    @Override
    public RoleDto updateRole(Long id , RoleDto roleDto)
    {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a role with the given id"));

        role.setName(roleDto.getName());
        Role savedRole = roleRepository.save(role);

        return roleMapper.toDto(savedRole);
    }

    @Override
    public List<RoleDto> updateAll(List<RoleDto> roleDtoList)
    {
        return roleDtoList
                .stream()
                .map(role -> updateRole(role.getId(),role)).toList();
    }

    @Override
    public void deleteRole(Long id)
    {
        RoleDto roleDto = findRoleById(id);

        roleRepository.delete(roleMapper.toEntity(roleDto));
    }
}
