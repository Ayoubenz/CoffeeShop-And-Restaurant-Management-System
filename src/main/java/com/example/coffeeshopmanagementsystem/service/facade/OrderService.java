package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.GetOrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    GetOrderDto placeOrderInShop(OrderPlacementDto orderPlacementDto);
    GetOrderDto getOrderById(Long id);
    List<GetOrderDto> getOrdersByCustomerId(Long id);
    List<GetOrderDto> getOrderByStatus(OrderStatus status);
    GetOrderDto getOrderByPaymentId(Long id);
    List<GetOrderDto> getAllOrders();
    void deleteOrder(Long id);

}
