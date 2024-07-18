package com.example.coffeeshopmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "suppliers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Product> product;
}
