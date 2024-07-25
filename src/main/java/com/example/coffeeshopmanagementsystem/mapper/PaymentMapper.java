package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.PaymentDto.CreatePaymentDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto.GetPaymentDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto.PaymentDto;
import com.example.coffeeshopmanagementsystem.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
    Payment toEntity(PaymentDto paymentDto);

    GetPaymentDto toGetDto(Payment payment);
    Payment toGetEntity(GetPaymentDto getPaymentDto);

    CreatePaymentDto toCreateDto(Payment payment);
    Payment toCreateEntity(CreatePaymentDto createPaymentDto);
}
