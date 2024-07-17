package com.example.coffeeshopmanagementsystem.security.service.impl;

import com.example.coffeeshopmanagementsystem.security.dto.CreatUserDto;
import com.example.coffeeshopmanagementsystem.security.dto.GetUserDto;
import com.example.coffeeshopmanagementsystem.security.entity.User;
import com.example.coffeeshopmanagementsystem.security.mapper.UserMapper;
import com.example.coffeeshopmanagementsystem.security.repository.UserRepository;
import com.example.coffeeshopmanagementsystem.security.service.facade.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GetUserDto findUserById(Long id)
    {
        User foundUser = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a user with the given Id"));
        log.info("The id of the user is {}", id);
        return userMapper.toGetDto(foundUser);
    }

    @Override
    public GetUserDto findUserByUsername(String username)
    {
        User foundUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a user with the given username"));
        return userMapper.toGetDto(foundUser);
    }

    @Override
    public CreatUserDto saveUser(CreatUserDto creatUserDto)
    {
        try {
            creatUserDto.setPassword(passwordEncoder.encode(creatUserDto.getPassword()));
            User savedUser = userRepository.save(userMapper.toCreateEntity(creatUserDto));
            return userMapper.toCreateDto(savedUser);
        }catch (Exception e)
        {
            throw new RuntimeException("Error happened while saving the user", e);
        }
    }

    @Override
    public List<CreatUserDto> saveAll(List<CreatUserDto> creatUserDtoList)
    {
        return creatUserDtoList.stream()
                .map(this::saveUser).toList();
    }

    @Override
    public CreatUserDto updateUser(Long id , CreatUserDto creatUserDto)
    {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find a user with the given Id"));
        foundUser.setUsername(creatUserDto.getUsername());

        // Check if password is provided before encoding
        if (creatUserDto.getPassword() != null && !creatUserDto.getPassword().isEmpty()) {
            foundUser.setPassword(passwordEncoder.encode(creatUserDto.getPassword()));
        }

        foundUser.setRoles(creatUserDto.getRoles());

        User savedUser = userRepository.save(foundUser);
        return userMapper.toCreateDto(savedUser);
    }

    @Override
    public void deleteUser(Long id)
    {
        GetUserDto foundUser = findUserById(id);
        userRepository.delete(userMapper.toGetEntity(foundUser));
    }
}
