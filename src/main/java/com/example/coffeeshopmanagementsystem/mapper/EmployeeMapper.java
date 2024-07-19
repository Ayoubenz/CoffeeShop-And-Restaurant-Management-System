package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.CreateEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.GetEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.EmployeeDto;
import com.example.coffeeshopmanagementsystem.entity.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {

    //Employee Dto
    EmployeeDto toDto (Employee employee);
    Employee toEntity (EmployeeDto employeeDto);

    //Get Employee Dto
    GetEmployeeDto toGetDto (Employee employee);
    Employee toGetEntity (GetEmployeeDto getEmployeeDto);

    //Create Employee Dto

    CreateEmployeeDto toCreateDto (Employee employee);
    Employee toCreateEntity( CreateEmployeeDto createEmployeeDto);
}
