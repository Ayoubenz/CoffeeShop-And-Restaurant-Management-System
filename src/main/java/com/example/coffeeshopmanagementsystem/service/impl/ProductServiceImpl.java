package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.ProductDto.CreateProductDto;
import com.example.coffeeshopmanagementsystem.dto.ProductDto.ProductDto;
import com.example.coffeeshopmanagementsystem.entity.OrderItem;
import com.example.coffeeshopmanagementsystem.entity.Product;
import com.example.coffeeshopmanagementsystem.exception.DataIntegrityException;
import com.example.coffeeshopmanagementsystem.exception.entities.ServiceException;
import com.example.coffeeshopmanagementsystem.mapper.OrderItemMapper;
import com.example.coffeeshopmanagementsystem.mapper.ProductMapper;
import com.example.coffeeshopmanagementsystem.repository.InventoryRepository;
import com.example.coffeeshopmanagementsystem.repository.ProductRepository;
import com.example.coffeeshopmanagementsystem.repository.SupplierRepository;
import com.example.coffeeshopmanagementsystem.service.facade.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SupplierRepository supplierRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderItemMapper orderItemMapper;
    @Override
    public ProductDto getProductById(Long id) {
        return productRepository
                .findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product Not found"));
    }

    @Override
    public List<ProductDto> getAllProduct() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto createProduct(CreateProductDto createProductDto) {
        try {
            Product product = productMapper.toCreateEntity(createProductDto);
            product.setSupplier(supplierRepository
                    .findById(createProductDto.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Supplier not found")));
            Product savedProduct = productRepository.save(product);
            return productMapper.toDto(savedProduct);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Invalid data: " + e.getMessage(), e);
        }catch (Exception e){
            throw new ServiceException("Failed to create the product: " + e.getMessage(), e);
        }
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            // Update simple fields
            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());

            // Set the Supplier entity
            existingProduct.setSupplier(supplierRepository
                    .findById(productDto.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Supplier Not found")));

            // Set the Inventory entity
            existingProduct.setInventory(inventoryRepository
                    .findById(productDto.getInventoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Inventory not found")));

            // Update OrderItems
            Set<OrderItem> orderItems = productDto.getOrderItems().stream()
                    .map(orderItemDto -> {
                        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                        orderItem.setProduct(existingProduct); // Ensure the relationship is correctly set
                        return orderItem;
                    })
                    .collect(Collectors.toSet());
            existingProduct.setOrderItems(orderItems);

            Product savedProduct = productRepository.save(existingProduct);
            return productMapper.toDto(savedProduct);
        }catch (Exception e){
            throw new ServiceException("Failed to update the product with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
