package com.example.coffeeshopmanagementsystem.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductDto {
    private String name;
    private String description;
    private double price;
    private Long supplierId;
}
