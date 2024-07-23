package com.example.coffeeshopmanagementsystem.mapper;

import com.example.coffeeshopmanagementsystem.dto.ProductDto.CreateProductDto;
import com.example.coffeeshopmanagementsystem.dto.ProductDto.ProductDto;
import com.example.coffeeshopmanagementsystem.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "inventory.id", target = "inventoryId")
    ProductDto toDto (Product product);

    @Mapping(source = "supplierId", target = "supplier.id")
    @Mapping(source = "inventoryId", target = "inventory.id")
    Product toEntity (ProductDto productDto);

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "price", target = "price")
    CreateProductDto toCreateDto(Product product);

    @Mapping(source = "supplierId", target = "supplier.id")
    @Mapping(source = "price", target = "price")
    Product toCreateEntity(CreateProductDto createProductDto);
}
