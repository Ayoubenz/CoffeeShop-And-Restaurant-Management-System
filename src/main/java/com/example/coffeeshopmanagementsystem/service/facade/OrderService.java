package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.GetOrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrderInShop(OrderPlacementDto orderPlacementDto);
    GetOrderDto getOrderById(Long id);
    List<GetOrderDto> getAllOrders();
}
