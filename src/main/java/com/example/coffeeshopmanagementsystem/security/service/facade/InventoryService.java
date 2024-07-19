package com.example.coffeeshopmanagementsystem.security.service.facade;

import com.example.coffeeshopmanagementsystem.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto);
    InventoryDto updateInventory(Long id, InventoryDto inventoryDto);
    InventoryDto getInventoryById(Long id);
    List<InventoryDto> getAllInventories();
    void deleteInventory(Long id);
}
