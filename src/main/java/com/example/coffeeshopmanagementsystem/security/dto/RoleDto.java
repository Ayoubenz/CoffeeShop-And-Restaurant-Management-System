package com.example.coffeeshopmanagementsystem.security.dto;

import com.example.coffeeshopmanagementsystem.security.entity.RoleName;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleName name;
}
