package com.example.coffeeshopmanagementsystem.security.controller;

import com.example.coffeeshopmanagementsystem.security.dto.CreatUserDto;
import com.example.coffeeshopmanagementsystem.security.dto.GetUserDto;
import com.example.coffeeshopmanagementsystem.security.service.facade.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        GetUserDto user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<GetUserDto> getUserByUsername(@PathVariable String username) {
        GetUserDto user = userService.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<CreatUserDto> saveUser(@RequestBody CreatUserDto creatUserDto) {
        CreatUserDto savedUser = userService.saveUser(creatUserDto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CreatUserDto>> saveAllUsers(@RequestBody List<CreatUserDto> creatUserDtos) {
        List<CreatUserDto> savedUsers = userService.saveAll(creatUserDtos);
        return ResponseEntity.ok(savedUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreatUserDto> updateUser(@PathVariable Long id, @RequestBody CreatUserDto creatUserDto) {
        CreatUserDto updatedUser = userService.updateUser(id, creatUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
