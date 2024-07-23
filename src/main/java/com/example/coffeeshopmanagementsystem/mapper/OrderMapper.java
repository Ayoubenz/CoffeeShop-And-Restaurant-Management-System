package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
