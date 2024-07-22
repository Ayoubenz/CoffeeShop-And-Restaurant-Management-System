package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.ShiftDto;

import java.util.List;

public interface ShiftService {

    ShiftDto getShiftById(Long id);
    List<ShiftDto> getAllShifts();
    ShiftDto createShift(ShiftDto shiftDto);
    ShiftDto updateShift(Long id, ShiftDto shiftDto);
    void deleteShift(Long id);
}
