package com.example.coffeeshopmanagementsystem.dto;

import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CustomerDto;
import com.example.coffeeshopmanagementsystem.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Double totalPrice;
    private CustomerDto customerDto;
    private Set<OrderItemDto> orderItemDtoss;
    private Set<PaymentDto> paymentDto;
}
