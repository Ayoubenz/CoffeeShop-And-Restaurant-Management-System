package com.example.coffeeshopmanagementsystem.dto;

import com.example.coffeeshopmanagementsystem.dto.ProductDto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private Double orderedPrice;
}
