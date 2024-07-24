package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.GetOrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);

    @Mapping(source = "customer.id",target = "customerId")
    GetOrderDto toGetDto(Order order);
    @Mapping(source = "customerId",target = "customer.id")
    Order toGetEntity(GetOrderDto getOrderDto);
}
