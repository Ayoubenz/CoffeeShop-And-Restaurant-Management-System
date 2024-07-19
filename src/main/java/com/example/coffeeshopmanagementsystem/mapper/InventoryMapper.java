package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.InventoryDto;
import com.example.coffeeshopmanagementsystem.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper
public interface InventoryMapper {

    InventoryDto toDto(Inventory inventory);
    Inventory toEntity(InventoryDto inventoryDto);
}
