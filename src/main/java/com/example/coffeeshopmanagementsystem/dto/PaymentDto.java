package com.example.coffeeshopmanagementsystem.dto;

import com.example.coffeeshopmanagementsystem.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private double amount;
    private PaymentMethod paymentMethod;
}
