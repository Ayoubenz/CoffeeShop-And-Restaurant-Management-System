package com.example.coffeeshopmanagementsystem.repository;

import com.example.coffeeshopmanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long id);
}
