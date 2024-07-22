package com.example.coffeeshopmanagementsystem.controller;

import com.example.coffeeshopmanagementsystem.dto.SupplierDto;
import com.example.coffeeshopmanagementsystem.service.facade.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        SupplierDto supplierDto = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplierDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<SupplierDto> supplierDtos = supplierService.getAllSuppliers();
        return new ResponseEntity<>(supplierDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierDto supplierDto) {
        SupplierDto createdSupplierDto = supplierService.createSupplier(supplierDto);
        return new ResponseEntity<>(createdSupplierDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Long id, @RequestBody SupplierDto supplierDto) {
        SupplierDto updatedSupplierDto = supplierService.updateSupplier(id, supplierDto);
        return new ResponseEntity<>(updatedSupplierDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
