package com.example.coffeeshopmanagementsystem.repository;


import com.example.coffeeshopmanagementsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
