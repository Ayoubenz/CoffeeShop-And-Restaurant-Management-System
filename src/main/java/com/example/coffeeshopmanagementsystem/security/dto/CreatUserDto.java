package com.example.coffeeshopmanagementsystem.security.dto;

import com.example.coffeeshopmanagementsystem.security.entity.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreatUserDto {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
