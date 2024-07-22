package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.SupplierDto;
import com.example.coffeeshopmanagementsystem.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper
public interface SupplierMapper {

    SupplierDto toDto(Supplier supplier);
    Supplier toEntity(SupplierDto supplierDto);
}
