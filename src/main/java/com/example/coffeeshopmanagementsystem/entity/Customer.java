package com.example.coffeeshopmanagementsystem.entity;

import com.example.coffeeshopmanagementsystem.security.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer extends User {
    private int loyaltyPoints = 0;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders;

}
