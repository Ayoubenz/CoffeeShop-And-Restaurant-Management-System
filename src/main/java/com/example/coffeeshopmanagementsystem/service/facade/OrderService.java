package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;

public interface OrderService {
    OrderDto placeOrder(OrderPlacementDto orderPlacementDto);
}
