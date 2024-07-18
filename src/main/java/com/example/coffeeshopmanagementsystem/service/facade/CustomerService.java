package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.CreateCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.GetCustomerDto;

import java.util.List;

public interface CustomerService {
    GetCustomerDto getCustomerById (Long id);
    List<GetCustomerDto> getAllCustomers();
    CreateCustomerDto createCustomer(CreateCustomerDto customerDTO);
    CustomerDto updateCustomer(Long id, CustomerDto customerDTO);
    void deleteCustomer(Long id);
}
