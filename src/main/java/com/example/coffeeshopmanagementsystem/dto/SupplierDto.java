package com.example.coffeeshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;
}
