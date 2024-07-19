package com.example.coffeeshopmanagementsystem.repository;

import com.example.coffeeshopmanagementsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
