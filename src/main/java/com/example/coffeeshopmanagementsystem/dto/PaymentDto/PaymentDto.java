package com.example.coffeeshopmanagementsystem.dto.PaymentDto;

import com.example.coffeeshopmanagementsystem.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private double amount;
    private PaymentMethod paymentMethod;
}
