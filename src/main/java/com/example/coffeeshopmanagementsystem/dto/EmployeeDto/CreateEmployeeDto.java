package com.example.coffeeshopmanagementsystem.dto.EmployeeDto;

import com.example.coffeeshopmanagementsystem.entity.enums.Position;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateEmployeeDto {
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
    private String name;
    private Position position;
}
