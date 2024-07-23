package com.example.coffeeshopmanagementsystem.dto.OrderDto;

import com.example.coffeeshopmanagementsystem.dto.OrderItemDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPlacementDto {
    private Long customerId;
    private Set<OrderItemDto> orderItems;
}
