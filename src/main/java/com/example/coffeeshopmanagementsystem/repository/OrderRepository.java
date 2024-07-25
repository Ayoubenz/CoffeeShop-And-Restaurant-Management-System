package com.example.coffeeshopmanagementsystem.repository;

import com.example.coffeeshopmanagementsystem.entity.Order;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long id);
    List<Order> findByStatus(OrderStatus status);
    @Query("SELECT o FROM orders o JOIN o.payments p WHERE p.id = :paymentId")
    Optional<Order> findOrderByPaymentId(@Param("paymentId") Long paymentId);

}
