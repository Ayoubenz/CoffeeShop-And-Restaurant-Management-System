package com.example.coffeeshopmanagementsystem.repository;

import com.example.coffeeshopmanagementsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long id);
}
