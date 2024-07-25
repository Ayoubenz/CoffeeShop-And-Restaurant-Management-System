package com.example.coffeeshopmanagementsystem.dto.ShitfDto;

import com.example.coffeeshopmanagementsystem.dto.EmployeeDto.EmployeeDto;
import com.example.coffeeshopmanagementsystem.dto.TaskDto;
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
    private Long employeeId;
    private List<TaskDto> tasks;
}
