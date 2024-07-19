package com.example.coffeeshopmanagementsystem.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEmployeeDetailsDto {
    private String username;
    private String password;
    private String name;
}
