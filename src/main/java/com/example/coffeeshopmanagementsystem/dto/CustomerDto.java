package com.example.coffeeshopmanagementsystem.dto;

import com.example.coffeeshopmanagementsystem.entity.Order;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private Set<Role> roles;
    private int loyaltyPoints;
    private Set<Order> orders;

}
