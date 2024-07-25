package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.ShitfDto.CreateShiftDto;
import com.example.coffeeshopmanagementsystem.dto.ShitfDto.ShiftDto;
import com.example.coffeeshopmanagementsystem.entity.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ShiftMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    ShiftDto toDto (Shift shift);
    @Mapping(source = "employeeId", target = "employee.id")
    Shift toEntity (ShiftDto shiftDto);

    @Mapping(source = "employee.id", target = "employeeId")
    CreateShiftDto toCreateDto (Shift shift);
    @Mapping(source = "employeeId", target = "employee.id")
    Shift toCreateEntity (CreateShiftDto createShiftDto);
}
