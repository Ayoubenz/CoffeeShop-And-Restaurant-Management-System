package com.example.coffeeshopmanagementsystem.dto.PaymentDto;

import com.example.coffeeshopmanagementsystem.entity.enums.PaymentMethod;
import com.example.coffeeshopmanagementsystem.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentDto {
    private double amount;
    private LocalDateTime orderDateAndTime;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private Long orderId;
}
