package com.example.coffeeshopmanagementsystem.security.service.impl.security;


import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.entity.RoleName;
import com.example.coffeeshopmanagementsystem.security.entity.User;
import com.example.coffeeshopmanagementsystem.security.repository.RoleRepository;
import com.example.coffeeshopmanagementsystem.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(String username, String password, Set<RoleName> roleNames) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists!");
        }

        Set<Role> foundRoles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(foundRoles);

        return userRepository.save(newUser);
    }
}
