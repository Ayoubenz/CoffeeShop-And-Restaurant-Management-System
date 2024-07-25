package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.CreateEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.EmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.GetEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.UpdateEmployeeDetailsDto;
import com.example.coffeeshopmanagementsystem.entity.Employee;
import com.example.coffeeshopmanagementsystem.entity.enums.Position;
import com.example.coffeeshopmanagementsystem.exception.DataIntegrityException;
import com.example.coffeeshopmanagementsystem.exception.entities.ServiceException;
import com.example.coffeeshopmanagementsystem.mapper.EmployeeMapper;
import com.example.coffeeshopmanagementsystem.repository.EmployeeRepository;
import com.example.coffeeshopmanagementsystem.security.dto.RoleDto;
import com.example.coffeeshopmanagementsystem.security.entity.Role;
import com.example.coffeeshopmanagementsystem.security.entity.RoleName;
import com.example.coffeeshopmanagementsystem.security.mapper.RoleMapper;
import com.example.coffeeshopmanagementsystem.security.repository.RoleRepository;
import com.example.coffeeshopmanagementsystem.security.service.facade.RoleService;
import com.example.coffeeshopmanagementsystem.service.facade.EmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final EntityManager entityManager;

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

            //Assigning the role
            Role role = getRoleByPosition(employee.getPosition());
            role = entityManager.merge(role);

            Set<Role> roles = new HashSet<>();
            roles.add(role);
            employee.setRoles(roles);
            Employee savedEmployee = employeeRepository.save(employee);
            return employeeMapper.toGetDto(savedEmployee);
        }catch (DataIntegrityViolationException e) {
            // Handle specific exceptions (e.g., if a unique constraint is violated)
            throw new DataIntegrityException("Invalid data: " + e.getMessage(), e);
        }catch (Exception e)
        {
            throw new ServiceException("Failed to create the employee : " + e.getMessage(), e);
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
            throw  new ServiceException("Failed to update employee with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public GetEmployeeDto updateEmployeeDetails(Long id, UpdateEmployeeDetailsDto updateEmployeeDetailsDto) {

        try {
            Employee foundEmployee = employeeRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id "+id));

            foundEmployee.setUsername(updateEmployeeDetailsDto.getUsername());
            foundEmployee.setPassword(passwordEncoder.encode(updateEmployeeDetailsDto.getPassword()));
            foundEmployee.setName(updateEmployeeDetailsDto.getName());

            Employee updatedEmployee = employeeRepository.save(foundEmployee);
            return employeeMapper.toGetDto(updatedEmployee);
        }catch (Exception e)
        {
            throw  new ServiceException("Failed to update employee with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        GetEmployeeDto foundEmployee = getEmployeeById(id); //it handles exceptions if not found
        employeeRepository.deleteById(id);
    }

    //Utility method - fin User role by employee position
    private Role getRoleByPosition(Position position){

        switch (position){
            case MANAGER -> {
                return roleMapper.toEntity(roleService.findRoleByName(RoleName.ROLE_ADMIN));
            }
            case BARISTA,CASHIER -> {
                return roleMapper.toEntity(roleService.findRoleByName(RoleName.ROLE_CASHIER));
            }
            case CLEANER,SERVER -> {
                return null;
            }default -> {
                throw new ServiceException("Unknown position: " + position);
            }
        }
    }
}
