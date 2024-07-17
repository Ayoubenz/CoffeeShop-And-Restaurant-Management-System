package com.example.coffeeshopmanagementsystem.security.controller;

import com.example.coffeeshopmanagementsystem.security.dto.CreatUserDto;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.entity.User;
import com.example.coffeeshopmanagementsystem.security.service.impl.security.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreatUserDto registrationDto) {
        try {
            User registeredUser = registrationService.registerNewUser(
                    registrationDto.getUsername(),
                    registrationDto.getPassword(),
                    registrationDto.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
            );
            return ResponseEntity.ok("User registered successfully with username: " + registeredUser.getUsername());
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
