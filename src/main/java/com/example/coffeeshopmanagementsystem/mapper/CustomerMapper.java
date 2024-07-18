package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.CreateCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.GetCustomerDto;
import com.example.coffeeshopmanagementsystem.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto toDto (Customer customer);
    Customer toEntity (CustomerDto customerDto);
    CreateCustomerDto toCreateDto (Customer customer);
    Customer toCreateEntity (CreateCustomerDto createCustomerDto);
    GetCustomerDto toGetDto (Customer customer);
    Customer toGetEntity (GetCustomerDto getCustomerDto);
}
