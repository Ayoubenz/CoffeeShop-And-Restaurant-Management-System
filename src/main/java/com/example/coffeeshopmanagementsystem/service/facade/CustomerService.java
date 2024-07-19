package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CreateCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.GetCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.UpdateCustomerDto;

import java.util.List;

public interface CustomerService {
    GetCustomerDto getCustomerById (Long id);
    List<GetCustomerDto> getAllCustomers();
    GetCustomerDto createCustomer(CreateCustomerDto customerDTO);
    GetCustomerDto updateCustomer(Long id, CustomerDto customerDTO);
    GetCustomerDto updateCustomerDetails (Long id , UpdateCustomerDto updateCustomerDto);
    void deleteCustomer(Long id);
}
