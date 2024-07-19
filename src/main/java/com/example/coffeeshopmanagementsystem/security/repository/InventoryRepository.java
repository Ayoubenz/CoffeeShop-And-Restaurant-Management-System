package com.example.coffeeshopmanagementsystem.security.repository;

import com.example.coffeeshopmanagementsystem.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
