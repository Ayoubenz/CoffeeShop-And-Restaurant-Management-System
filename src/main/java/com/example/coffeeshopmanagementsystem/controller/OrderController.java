package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;
import com.example.coffeeshopmanagementsystem.service.facade.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/place-order")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderPlacementDto orderPlacementDto){
        OrderDto placedOrder = orderService.placeOrder(orderPlacementDto);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }
}
