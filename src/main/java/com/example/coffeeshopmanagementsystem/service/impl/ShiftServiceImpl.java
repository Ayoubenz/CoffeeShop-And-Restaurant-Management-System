package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.ShiftDto;
import com.example.coffeeshopmanagementsystem.entity.Employee;
import com.example.coffeeshopmanagementsystem.entity.Shift;
import com.example.coffeeshopmanagementsystem.exception.DataIntegrityException;
import com.example.coffeeshopmanagementsystem.exception.entities.ServiceException;
import com.example.coffeeshopmanagementsystem.mapper.ShiftMapper;
import com.example.coffeeshopmanagementsystem.mapper.TaskMapper;
import com.example.coffeeshopmanagementsystem.repository.EmployeeRepository;
import com.example.coffeeshopmanagementsystem.repository.ShiftRepository;
import com.example.coffeeshopmanagementsystem.service.facade.ShiftService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;
    private final EmployeeRepository employeeRepository;
    private final TaskMapper taskMapper;

    @Override
    public ShiftDto getShiftById(Long id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found"));
        return shiftMapper.toDto(shift);
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        List<Shift> shifts = shiftRepository.findAll();
        return shifts.stream()
                .map(shiftMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ShiftDto createShift(ShiftDto shiftDto) {
        try {
            Shift shift = shiftMapper.toEntity(shiftDto);
            //Checking if Employee exist retrieving it then save it to the shift with its id
            Employee employee = employeeRepository.findById(shiftDto.getEmployee().getId()).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
            shift.setEmployee(employee);
            Shift savedShift = shiftRepository.save(shift);
            return shiftMapper.toDto(savedShift);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Invalid data: " + e.getMessage(), e);
        }catch (Exception e){
            throw new ServiceException("Failed to create the shift: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public ShiftDto updateShift(Long id, ShiftDto shiftDto) {
        try {
            Shift existingShift = shiftRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Shift not found"));

            // Update fields
            existingShift.setStartTime(shiftDto.getStartTime());
            existingShift.setEndTime(shiftDto.getEndTime());
            existingShift.setEmployee(employeeRepository.findById(shiftDto.getEmployee().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found")));
            existingShift.setTasks(shiftDto.getTasks().stream()
                    .map(taskMapper::toEntity)
                    .toList());

            Shift updatedShift = shiftRepository.save(existingShift);
            return shiftMapper.toDto(updatedShift);
        }catch (Exception e){
            throw new ServiceException("Failed to update the shift with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteShift(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new EntityNotFoundException("Shift not found");
        }
        shiftRepository.deleteById(id);
    }
}