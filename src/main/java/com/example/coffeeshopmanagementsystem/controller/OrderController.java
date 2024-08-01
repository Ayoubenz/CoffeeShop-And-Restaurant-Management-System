package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.GetOrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
import com.example.coffeeshopmanagementsystem.service.facade.OrderService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/place-order")
    public ResponseEntity<GetOrderDto> placeOrder(@RequestBody OrderPlacementDto orderPlacementDto){
        GetOrderDto placedOrder = orderService.placeOrderInShop(orderPlacementDto);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @PostMapping("/pay-online")
    public ResponseEntity<GetOrderDto> placeOrderOnline(@RequestBody OrderPlacementDto orderPlacementDto) throws StripeException {
        GetOrderDto order = orderService.placeOrderOnline(orderPlacementDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetOrderDto> getOrderById(@PathVariable Long id){
        GetOrderDto order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<GetOrderDto>> getOrdersByCustomerId(@PathVariable Long id){
        List<GetOrderDto> orders = orderService.getOrdersByCustomerId(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<GetOrderDto> getOrderByPaymentId(@PathVariable Long id){
        GetOrderDto order = orderService.getOrderByPaymentId(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<GetOrderDto>> getOrderByStatus(@PathVariable OrderStatus status){
        List<GetOrderDto> orders = orderService.getOrderByStatus(status);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetOrderDto>> getAllOrders(){
        List<GetOrderDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
