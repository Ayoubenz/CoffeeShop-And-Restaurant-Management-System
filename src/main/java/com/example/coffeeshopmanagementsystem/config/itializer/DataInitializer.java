package com.example.coffeeshopmanagementsystem.config.itializer;

import com.example.coffeeshopmanagementsystem.entity.Employee;
import com.example.coffeeshopmanagementsystem.entity.enums.Position;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.entity.RoleName;
import com.example.coffeeshopmanagementsystem.security.repository.RoleRepository;
import com.example.coffeeshopmanagementsystem.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole;

        var roleOpt = roleRepository.findByName(RoleName.ROLE_ADMIN);
        if (roleOpt.isPresent()) {
            // If the role already exists, use it directly from the persistent context
            adminRole = roleOpt.get();
        } else {
            // If the role doesn't exist, create and persist a new instance
            adminRole = new Role();
            adminRole.setName(RoleName.ROLE_ADMIN);
            adminRole = roleRepository.save(adminRole); // Persist and re-assign to catch any managed version
        }

        if(roleRepository.findByName(RoleName.ROLE_CUSTOMER).isEmpty()){
            Role customerRole = new Role();
            customerRole.setName(RoleName.ROLE_CUSTOMER);
            roleRepository.save(customerRole);
        }
        if(roleRepository.findByName(RoleName.ROLE_SERVER).isEmpty()){
            Role customerRole = new Role();
            customerRole.setName(RoleName.ROLE_SERVER);
            roleRepository.save(customerRole);
        }
        if(roleRepository.findByName(RoleName.ROLE_CASHIER).isEmpty()){
            Role customerRole = new Role();
            customerRole.setName(RoleName.ROLE_CASHIER);
            roleRepository.save(customerRole);
        }

        // Check if an admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            Employee admin = new Employee();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setPosition(Position.MANAGER);
            // Associate the ADMIN role with the new user
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole); // adminRole is managed, either fetched or newly persisted
            admin.setRoles(adminRoles);

            userRepository.save(admin); // Persist the new admin user with its roles
        }
    }
}
