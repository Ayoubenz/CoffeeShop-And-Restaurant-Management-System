package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.OrderItemDto;
import com.example.coffeeshopmanagementsystem.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(source = "productId", target = "product.id")
    OrderItem toEntity(OrderItemDto orderItemDto);
}
