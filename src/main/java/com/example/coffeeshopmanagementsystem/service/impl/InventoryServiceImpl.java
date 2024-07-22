package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.InventoryDto;
import com.example.coffeeshopmanagementsystem.entity.Inventory;
import com.example.coffeeshopmanagementsystem.exception.DataIntegrityException;
import com.example.coffeeshopmanagementsystem.exception.entities.ServiceException;
import com.example.coffeeshopmanagementsystem.mapper.InventoryMapper;
import com.example.coffeeshopmanagementsystem.repository.ProductRepository;
import com.example.coffeeshopmanagementsystem.repository.InventoryRepository;
import com.example.coffeeshopmanagementsystem.service.facade.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        try {
            Inventory inventory = inventoryMapper.toEntity(inventoryDto);
            inventory.setProduct(productRepository.findById(inventoryDto.getProductDto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found")));
            Inventory savedInventory = inventoryRepository.save(inventory);
            return inventoryMapper.toDto(savedInventory);
        }catch (DataIntegrityViolationException e) {
            // Handle specific exceptions (e.g., if a unique constraint is violated)
            throw new DataIntegrityException("Invalid data: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new ServiceException("Failed to create the inventory: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) {
        try {
            Inventory existingInventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
            existingInventory.setProduct(productRepository.findById(inventoryDto.getProductDto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found")));
            existingInventory.setStockQuantity(inventoryDto.getStockQuantity());
            existingInventory.setRestockDate(inventoryDto.getRestockDate());
            Inventory updatedInventory = inventoryRepository.save(existingInventory);
            return inventoryMapper.toDto(updatedInventory);
        }catch (Exception e){
            throw new ServiceException("Failed to update the inventory with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public InventoryDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Inventory not found");
        }
        inventoryRepository.deleteById(id);
    }
}
