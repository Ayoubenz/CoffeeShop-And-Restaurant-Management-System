package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.CreateEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.EmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.GetEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.UpdateEmployeeDetailsDto;
import com.example.coffeeshopmanagementsystem.entity.Employee;
import com.example.coffeeshopmanagementsystem.exception.entities.EmployeeException;
import com.example.coffeeshopmanagementsystem.mapper.EmployeeMapper;
import com.example.coffeeshopmanagementsystem.repository.EmployeeRepository;
import com.example.coffeeshopmanagementsystem.service.facade.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GetEmployeeDto getEmployeeById(Long id) {
        return employeeRepository
                .findById(id)
                .map(employeeMapper::toGetDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id "+id));
    }

    @Override
    public List<GetEmployeeDto> getAllEmployee() {

        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()){
            throw new EntityNotFoundException("No employees found");
        }
        return employeeRepository
                .findAll()
                .stream()
                .map(employeeMapper::toGetDto)
                .toList();
    }

    @Override
    @Transactional
    public GetEmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto) {
        try {
            String encryptedPassword = passwordEncoder.encode(createEmployeeDto.getPassword());
            Employee employee = employeeMapper.toCreateEntity(createEmployeeDto);
            employee.setPassword(encryptedPassword);

            Employee savedEmployee = employeeRepository.save(employee);
            return employeeMapper.toGetDto(savedEmployee);
        }catch (DataIntegrityViolationException e) {
            // Handle specific exceptions (e.g., if a unique constraint is violated)
            throw new IllegalArgumentException("Invalid data: " + e.getMessage(), e);
        }catch (Exception e)
        {
            throw new EmployeeException("Failed to create the Customer: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public GetEmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

        try {
            GetEmployeeDto foundEmployeeDto = getEmployeeById(id);
            Employee foundEmployee = employeeMapper.toGetEntity(foundEmployeeDto);

            foundEmployee.setUsername(employeeDto.getUsername());
            foundEmployee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
            foundEmployee.setName(employeeDto.getName());
            foundEmployee.setRoles(employeeDto.getRoles());
            foundEmployee.setPosition(employeeDto.getPosition());
            foundEmployee.setSchedule(employeeDto.getSchedule());

            Employee updatedEmployee = employeeRepository.save(foundEmployee);
            return employeeMapper.toGetDto(updatedEmployee);
        }catch (Exception e)
        {
            throw  new EmployeeException("Failed to update employee with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public GetEmployeeDto updateEmployeeDetails(Long id, UpdateEmployeeDetailsDto updateEmployeeDetailsDto) {

        try {
            Employee foundEmployee = employeeRepository
                    .findById(id)
                    .orElseThrow(() -> new EmployeeException("Employee not found with id "+id));

            foundEmployee.setUsername(updateEmployeeDetailsDto.getUsername());
            foundEmployee.setPassword(passwordEncoder.encode(updateEmployeeDetailsDto.getPassword()));
            foundEmployee.setName(updateEmployeeDetailsDto.getName());

            Employee updatedEmployee = employeeRepository.save(foundEmployee);
            return employeeMapper.toGetDto(updatedEmployee);
        }catch (Exception e)
        {
            throw  new EmployeeException("Failed to update employee with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        GetEmployeeDto foundEmployee = getEmployeeById(id); //it handles exceptions if not found
        employeeRepository.deleteById(id);
    }
}
