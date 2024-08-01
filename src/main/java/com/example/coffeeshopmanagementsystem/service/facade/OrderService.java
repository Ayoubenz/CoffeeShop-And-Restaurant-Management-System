package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.GetOrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
import com.stripe.exception.StripeException;

import java.util.List;

public interface OrderService {
    GetOrderDto placeOrderInShop(OrderPlacementDto orderPlacementDto);
    GetOrderDto placeOrderOnline(OrderPlacementDto orderPlacementDto) throws StripeException;
    GetOrderDto getOrderById(Long id);
    List<GetOrderDto> getOrdersByCustomerId(Long id);
    List<GetOrderDto> getOrderByStatus(OrderStatus status);
    GetOrderDto getOrderByPaymentId(Long id);
    List<GetOrderDto> getAllOrders();
    void deleteOrder(Long id);

}
