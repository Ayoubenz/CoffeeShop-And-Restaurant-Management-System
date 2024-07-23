package com.example.coffeeshopmanagementsystem.dto.ProductDto;

import com.example.coffeeshopmanagementsystem.dto.InventoryDto;
import com.example.coffeeshopmanagementsystem.dto.OrderItemDto;
import com.example.coffeeshopmanagementsystem.dto.SupplierDto;
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
    private double price;
    private Long supplierId;
    private Long inventoryId;
    private Set<OrderItemDto> orderItems;
}
