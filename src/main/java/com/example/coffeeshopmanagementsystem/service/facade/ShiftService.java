package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.ShitfDto.CreateShiftDto;
import com.example.coffeeshopmanagementsystem.dto.ShitfDto.ShiftDto;

import java.util.List;

public interface ShiftService {

    ShiftDto getShiftById(Long id);
    List<ShiftDto> getAllShifts();
    List<ShiftDto> getShiftByEmployeeId(Long employeeId);
    ShiftDto createShift(CreateShiftDto createShiftDto);
    ShiftDto updateShift(Long id, ShiftDto shiftDto);
    void deleteShift(Long id);
}
