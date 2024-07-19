package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CreateCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.GetCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.UpdateCustomerDto;
import com.example.coffeeshopmanagementsystem.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    //CustomerDto
    CustomerDto toDto (Customer customer);
    Customer toEntity (CustomerDto customerDto);

    //CreateCustomerDto
    CreateCustomerDto toCreateDto (Customer customer);
    Customer toCreateEntity (CreateCustomerDto createCustomerDto);

    //GetCustomerDto
    GetCustomerDto toGetDto (Customer customer);
    Customer toGetEntity (GetCustomerDto getCustomerDto);

    //UpdateCustomerDto
    UpdateCustomerDto toUpdateDto (Customer customer);
    Customer toEntityDto (UpdateCustomerDto updateCustomerDto);
}
