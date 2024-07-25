package com.example.coffeeshopmanagementsystem.dto.EmployeeDto;

import com.example.coffeeshopmanagementsystem.dto.ShitfDto.ShiftDto;
import com.example.coffeeshopmanagementsystem.entity.enums.Position;
import com.example.coffeeshopmanagementsystem.entity.Shift;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetEmployeeDto {

    private Long id;
    private String username;
    private String name;
    private Set<Role> roles;
    private Position position;
    private List<ShiftDto> schedule;

}
