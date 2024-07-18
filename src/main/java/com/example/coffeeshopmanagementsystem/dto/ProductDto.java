package com.example.coffeeshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private SupplierDto supplier;
    private InventoryDto inventory;
    private Set<OrderItemDto> orderItems;
}
