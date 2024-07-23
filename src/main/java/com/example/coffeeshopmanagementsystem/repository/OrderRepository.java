package com.example.coffeeshopmanagementsystem.repository;

import com.example.coffeeshopmanagementsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
