package com.example.coffeeshopmanagementsystem.dto;

import com.example.coffeeshopmanagementsystem.dto.ProductDto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDto {

    private Long id;
    private ProductDto productDto;
    private int stockQuantity;
    private LocalDateTime restockDate;
}
