package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.PaymentDto.CreatePaymentDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto.GetPaymentDto;

import java.util.List;

public interface PaymentService {
    GetPaymentDto getPaymentById(Long id);
    List<GetPaymentDto> getPaymentsByOrderId(Long id);
    GetPaymentDto addPayment(Long orderId, CreatePaymentDto createPaymentDto);
    List<GetPaymentDto> getAllpayments();
    GetPaymentDto updatePayment(Long id, CreatePaymentDto createPaymentDto);
    void deletePayment(Long id);
}
