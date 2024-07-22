package com.example.coffeeshopmanagementsystem.entity;

import com.example.coffeeshopmanagementsystem.security.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "employees")
@DiscriminatorValue("EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee extends User {

    @Enumerated(EnumType.STRING)
    private Position position;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shift> schedule;
}
