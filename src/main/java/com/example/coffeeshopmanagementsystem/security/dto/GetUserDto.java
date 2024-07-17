package com.example.coffeeshopmanagementsystem.security.dto;

import com.example.coffeeshopmanagementsystem.security.entity.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetUserDto {
    private Long id;
    private String username;
    private Set<Role> roles = new HashSet<>();
}
