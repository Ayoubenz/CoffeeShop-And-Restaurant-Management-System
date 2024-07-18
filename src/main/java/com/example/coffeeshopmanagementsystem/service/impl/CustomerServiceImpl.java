package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.CreateCustomerDto;
import com.example.coffeeshopmanagementsystem.dto.CustomerDto;
import com.example.coffeeshopmanagementsystem.dto.GetCustomerDto;
import com.example.coffeeshopmanagementsystem.entity.Customer;
import com.example.coffeeshopmanagementsystem.mapper.CustomerMapper;
import com.example.coffeeshopmanagementsystem.repository.CustomerRepository;
import com.example.coffeeshopmanagementsystem.service.facade.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public GetCustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toGetDto)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<GetCustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty())
            throw new EntityNotFoundException("No customers found");

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toGetDto)
                .toList();
    }

    @Override
    @Transactional
    public CreateCustomerDto createCustomer(CreateCustomerDto customerDTO) {
        try{
            String encryptedPassword = passwordEncoder.encode(customerDTO.getPassword());
            Customer customer = customerMapper.toCreateEntity(customerDTO);
            customer.setPassword(encryptedPassword);
            Customer savedCustomer = customerRepository.save(customer);
            return customerMapper.toCreateDto(savedCustomer);
        } catch (DataIntegrityViolationException e) {
            // Handle specific exceptions (e.g., if a unique constraint is violated)
            throw new IllegalArgumentException("Invalid data: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RuntimeException("Failed to create the Customer: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDTO) {

        try{
            GetCustomerDto foundCustomerDto = getCustomerById(id);
            Customer foundCustomer = customerMapper.toGetEntity(foundCustomerDto);

            foundCustomer.setUsername(customerDTO.getUsername());
            foundCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
            foundCustomer.setName(customerDTO.getName());
            foundCustomer.setRoles(customerDTO.getRoles());
            foundCustomer.setLoyaltyPoints(customerDTO.getLoyaltyPoints());
            foundCustomer.setOrders(customerDTO.getOrders());

            Customer updatedCustomer = customerRepository.save(foundCustomer);
            return customerMapper.toDto(updatedCustomer);
        }catch (Exception e)
        {
            throw  new RuntimeException("Failed to update customer with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        GetCustomerDto customerDto = getCustomerById(id);
        customerRepository.deleteById(id);
    }
}
