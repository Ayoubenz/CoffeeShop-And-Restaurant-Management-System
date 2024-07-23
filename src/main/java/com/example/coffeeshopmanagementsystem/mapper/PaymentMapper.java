package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.PaymentDto;
import com.example.coffeeshopmanagementsystem.entity.Payment;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
    Payment toEntity(PaymentDto paymentDto);
}
