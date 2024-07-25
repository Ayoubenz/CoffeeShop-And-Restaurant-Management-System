package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.ShitfDto.CreateShiftDto;
import com.example.coffeeshopmanagementsystem.dto.ShitfDto.ShiftDto;
import com.example.coffeeshopmanagementsystem.service.facade.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shift")
@RequiredArgsConstructor
public class ShiftController {
    private final ShiftService shiftService;

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDto> getShiftById(@PathVariable Long id) {
        ShiftDto shiftDto = shiftService.getShiftById(id);
        return new ResponseEntity<>(shiftDto, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ShiftDto>> getShiftByEmployeeId(@PathVariable Long employeeId) {
        List<ShiftDto> shifts = shiftService.getShiftByEmployeeId(employeeId);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShiftDto>> getAllShifts() {
        List<ShiftDto> shiftDtos = shiftService.getAllShifts();
        return new ResponseEntity<>(shiftDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ShiftDto> createShift(@RequestBody CreateShiftDto createShiftDto) {
        ShiftDto createdShiftDto = shiftService.createShift(createShiftDto);
        return new ResponseEntity<>(createdShiftDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDto> updateShift(@PathVariable Long id, @RequestBody ShiftDto shiftDto) {
        ShiftDto updatedShiftDto = shiftService.updateShift(id, shiftDto);
        return new ResponseEntity<>(updatedShiftDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
