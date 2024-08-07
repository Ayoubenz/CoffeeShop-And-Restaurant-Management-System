package com.example.coffeeshopmanagementsystem.security.repository;

import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (RoleName name);

}
