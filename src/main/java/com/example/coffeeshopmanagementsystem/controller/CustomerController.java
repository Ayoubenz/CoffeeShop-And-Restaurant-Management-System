package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CreateCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.GetCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto.UpdateCustomerDto;
import com.example.coffeeshopmanagementsystem.service.facade.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerDto> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetCustomerDto>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GetCustomerDto> createCustomer(@RequestBody CreateCustomerDto createCustomerDto)
    {
        GetCustomerDto savedCustomer = customerService.createCustomer(createCustomerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetCustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto){
        GetCustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @PutMapping("/update-customer/{id}")
    public ResponseEntity<GetCustomerDto> updateCustomerDetails(@PathVariable Long id, @RequestBody UpdateCustomerDto updateCustomerDto){
        GetCustomerDto updatedCustomer = customerService.updateCustomerDetails(id, updateCustomerDto);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
