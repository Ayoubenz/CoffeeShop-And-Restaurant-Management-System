package com.example.coffeeshopmanagementsystem.security.mapper;

import com.example.coffeeshopmanagementsystem.security.dto.CreatUserDto;
import com.example.coffeeshopmanagementsystem.security.dto.GetUserDto;
import com.example.coffeeshopmanagementsystem.security.entity.User;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    CreatUserDto toCreateDto(User user);
    User toCreateEntity(CreatUserDto creatUserDto);

    GetUserDto toGetDto(User user);
    User toGetEntity(GetUserDto getUserDto);
}
