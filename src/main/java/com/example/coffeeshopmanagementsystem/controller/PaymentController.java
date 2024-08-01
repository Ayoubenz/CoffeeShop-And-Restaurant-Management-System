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

    @GetMapping("{id}")
    public ResponseEntity<GetPaymentDto> getPaymentById(@PathVariable Long id){
        GetPaymentDto payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<List<GetPaymentDto>> getPaymentsByOrderId(@PathVariable Long id){
        List<GetPaymentDto> payments = paymentService.getPaymentsByOrderId(id);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetPaymentDto>> getAllPayments(){
        List<GetPaymentDto> payments = paymentService.getAllpayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GetPaymentDto> updatePayment(@PathVariable Long id,@RequestBody CreatePaymentDto createPaymentDto){
        GetPaymentDto updatedPayment = paymentService.updatePayment(id, createPaymentDto);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess() {
        return ResponseEntity.ok("Payment successful! Thank you for your purchase.");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancelled() {
        return ResponseEntity.ok("Payment was cancelled. Please try again.");
    }
}
