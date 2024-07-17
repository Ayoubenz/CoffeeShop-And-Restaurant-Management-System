package com.example.coffeeshopmanagementsystem.entity;

import com.example.coffeeshopmanagementsystem.security.entity.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer extends User {
    private int loyaltyPoints;

}
