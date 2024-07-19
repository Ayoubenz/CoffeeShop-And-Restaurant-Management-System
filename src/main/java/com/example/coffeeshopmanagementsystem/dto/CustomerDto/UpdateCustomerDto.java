package com.example.coffeeshopmanagementsystem.dto.CustomerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCustomerDto {
    private String name;
    private String username;
    private String password;
}
