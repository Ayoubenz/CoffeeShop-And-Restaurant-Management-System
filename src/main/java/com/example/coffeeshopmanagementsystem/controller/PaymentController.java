package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.PaymentDto.CreatePaymentDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto.GetPaymentDto;
import com.example.coffeeshopmanagementsystem.service.facade.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/add/{orderId}")
    public ResponseEntity<GetPaymentDto> addPayment(
            @PathVariable Long orderId,
            @RequestBody CreatePaymentDto createPaymentDto) {
        GetPaymentDto payment = paymentService.addPayment(orderId, createPaymentDto);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<List<GetPaymentDto>> getPaymentsByOrderId(@PathVariable Long id){
        List<GetPaymentDto> payments = paymentService.getPaymentsByOrderId(id);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}