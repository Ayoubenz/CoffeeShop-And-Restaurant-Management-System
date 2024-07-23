package com.example.coffeeshopmanagementsystem.repository;

import com.example.coffeeshopmanagementsystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
