package com.example.coffeeshopmanagementsystem.dto.OrderDto;

import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.OrderItemDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto.PaymentDto;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
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
    private CustomerDto customer;
    private Set<OrderItemDto> orderItems;
    private Set<PaymentDto> payments;
}