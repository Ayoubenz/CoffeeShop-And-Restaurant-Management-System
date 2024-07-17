package com.example.coffeeshopmanagementsystem.security.controller;

import com.example.coffeeshopmanagementsystem.security.dto.RoleDto;
import com.example.coffeeshopmanagementsystem.security.service.facade.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto role = roleService.findRoleById(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleDto> getRoleByRoleName(@PathVariable String name) {
        RoleDto role = roleService.findRoleByName(name);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto) {
        RoleDto savedRole = roleService.saveRole(roleDto);
        return ResponseEntity.ok(savedRole);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<RoleDto>> saveAllRoles(@RequestBody List<RoleDto> roleDtos) {
        List<RoleDto> savedRoles = roleService.saveAll(roleDtos);
        return ResponseEntity.ok(savedRoles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        RoleDto updatedRole = roleService.updateRole(id, roleDto);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }
}
