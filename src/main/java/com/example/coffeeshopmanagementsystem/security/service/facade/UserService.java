package com.example.coffeeshopmanagementsystem.security.service.facade;

import com.example.coffeeshopmanagementsystem.security.dto.CreatUserDto;
import com.example.coffeeshopmanagementsystem.security.dto.GetUserDto;


import java.util.List;

public interface UserService {
    GetUserDto findUserById(Long id);

    GetUserDto findUserByUsername(String username);

    List<GetUserDto> findAllUsers();

    CreatUserDto saveUser(CreatUserDto creatUserDto);

    List<CreatUserDto> saveAll(List<CreatUserDto> creatUserDtoList);

    CreatUserDto updateUser(Long id, CreatUserDto creatUserDto);

    void deleteUser(Long id);
}
