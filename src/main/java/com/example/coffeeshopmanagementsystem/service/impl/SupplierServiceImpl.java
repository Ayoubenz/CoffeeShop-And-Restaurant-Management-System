package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.SupplierDto;
import com.example.coffeeshopmanagementsystem.entity.Supplier;
import com.example.coffeeshopmanagementsystem.mapper.SupplierMapper;
import com.example.coffeeshopmanagementsystem.repository.SupplierRepository;
import com.example.coffeeshopmanagementsystem.service.facade.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public SupplierDto getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        return supplierMapper.toDto(supplier);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplierMapper::toDto)
                .toList();
    }

    @Override
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierMapper.toEntity(supplierDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(savedSupplier);
    }

    @Override
    public SupplierDto updateSupplier(Long id, SupplierDto supplierDto) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        // Update fields
        existingSupplier.setName(supplierDto.getName());
        existingSupplier.setPhoneNumber(supplierDto.getPhoneNumber());
        existingSupplier.setEmail(supplierDto.getEmail());
        existingSupplier.setAddress(supplierDto.getAddress());
        existingSupplier.setDescription(supplierDto.getDescription());

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return supplierMapper.toDto(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new EntityNotFoundException("Supplier not found");
        }
        supplierRepository.deleteById(id);
    }
}
