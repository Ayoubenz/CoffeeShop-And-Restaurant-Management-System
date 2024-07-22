package com.example.coffeeshopmanagementsystem.config;
import com.example.coffeeshopmanagementsystem.mapper.*;
import com.example.coffeeshopmanagementsystem.security.mapper.RoleMapper;
import com.example.coffeeshopmanagementsystem.security.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public RoleMapper roleMapper() {
        return Mappers.getMapper(RoleMapper.class);
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public CustomerMapper customerMapper(){
        return Mappers.getMapper(CustomerMapper.class);
    }
    @Bean
    public EmployeeMapper employeeMapper(){
        return Mappers.getMapper(EmployeeMapper.class);
    }

    @Bean
    public InventoryMapper inventoryMapper(){
        return Mappers.getMapper(InventoryMapper.class);
    }

    @Bean
    public TaskMapper taskMapper(){
        return Mappers.getMapper(TaskMapper.class);
    }

    @Bean
    public ProductMapper productMapper(){
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean OrderItemMapper orderItemMapper(){
        return Mappers.getMapper(OrderItemMapper.class);
    }

    @Bean
    SupplierMapper supplierMapper(){
        return Mappers.getMapper(SupplierMapper.class);
    }
}
