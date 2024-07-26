package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.CreateEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.EmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.GetEmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.UpdateEmployeeDetailsDto;
import com.example.coffeeshopmanagementsystem.service.facade.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<GetEmployeeDto> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetEmployeeDto>> getAllEmployee() {
        return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<GetEmployeeDto> createEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {

        GetEmployeeDto createdEmployee = employeeService.createEmployee(createEmployeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetEmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.updateEmployee(id,employeeDto), HttpStatus.OK);
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<GetEmployeeDto> updateEmployeeDetails(@PathVariable Long id, @RequestBody UpdateEmployeeDetailsDto updateEmployeeDetailsDto) {
        return new ResponseEntity<>(employeeService.updateEmployeeDetails(id,updateEmployeeDetailsDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
