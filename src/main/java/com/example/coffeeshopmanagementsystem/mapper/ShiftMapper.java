package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.ShiftDto;
import com.example.coffeeshopmanagementsystem.entity.Shift;
import org.mapstruct.Mapper;

@Mapper
public interface ShiftMapper {

    ShiftDto toDto (Shift shift);
    Shift toEntity (ShiftDto shiftDto);
}
