package com.example.coffeeshopmanagementsystem.service.facade;

import com.example.coffeeshopmanagementsystem.dto.ProductDto.CreateProductDto;
import com.example.coffeeshopmanagementsystem.dto.ProductDto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProduct();
    ProductDto createProduct(CreateProductDto createProductDto);
    ProductDto updateProduct(Long id , ProductDto productDto);
    void deleteProduct(Long id);
}
