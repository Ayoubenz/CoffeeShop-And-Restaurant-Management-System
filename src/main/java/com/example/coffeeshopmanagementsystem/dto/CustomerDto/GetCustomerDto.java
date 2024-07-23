package com.example.coffeeshopmanagementsystem.dto.CustomerDto;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.entity.Order;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCustomerDto {

    private Long id;
    private String name;
    private String username;
    private Set<Role> roles;
    private int loyaltyPoints;
    @JsonIgnore
    private Set<OrderDto> orders;
}
