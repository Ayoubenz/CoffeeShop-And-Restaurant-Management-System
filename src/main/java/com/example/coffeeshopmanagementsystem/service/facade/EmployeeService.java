package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.CreateEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.EmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.GetEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.UpdateEmployeeDetailsDto;

import java.util.List;

public interface EmployeeService {

    GetEmployeeDto getEmployeeById(Long id);
    List<GetEmployeeDto> getAllEmployee();
    GetEmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto);
    GetEmployeeDto updateEmployee(Long id , EmployeeDto employeeDto);
    GetEmployeeDto updateEmployeeDetails(Long id, UpdateEmployeeDetailsDto updateEmployeeDetailsDto);
    void deleteEmployee(Long id);
}
