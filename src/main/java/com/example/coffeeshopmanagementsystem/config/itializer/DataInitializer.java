package com.example.coffeeshopmanagementsystem.config.itializer;

import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.entity.User;
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
    @Transactional // Ensure the method is transactional to maintain persistence context
    public void run(String... args) throws Exception {
        Role adminRole;

        var roleOpt = roleRepository.findByName("ROLE_ADMIN");
        if (roleOpt.isPresent()) {
            // If the role already exists, use it directly from the persistent context
            adminRole = roleOpt.get();
        } else {
            // If the role doesn't exist, create and persist a new instance
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole = roleRepository.save(adminRole); // Persist and re-assign to catch any managed version
        }

        // Check if an admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));

            // Associate the ADMIN role with the new user
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole); // adminRole is managed, either fetched or newly persisted
            admin.setRoles(adminRoles);

            userRepository.save(admin); // Persist the new admin user with its roles
        }
    }
}
