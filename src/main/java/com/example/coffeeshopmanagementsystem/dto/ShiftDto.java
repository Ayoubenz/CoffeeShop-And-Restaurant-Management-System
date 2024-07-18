package com.example.coffeeshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShiftDto {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EmployeeDto employee;
    private List<TaskDto> tasks;
}
