package com.example.coffeeshopmanagementsystem.dto;

import com.example.coffeeshopmanagementsystem.entity.Customer;
import com.example.coffeeshopmanagementsystem.entity.OrderItem;
import com.example.coffeeshopmanagementsystem.entity.OrderStatus;
import com.example.coffeeshopmanagementsystem.entity.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Double totalPrice;
    private CustomerDto customerDto;
    private Set<OrderItemDto> orderItemDtoss;
    private Set<PaymentDto> paymentDto;
}
