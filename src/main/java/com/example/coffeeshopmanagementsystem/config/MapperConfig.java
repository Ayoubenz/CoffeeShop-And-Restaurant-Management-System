package com.example.coffeeshopmanagementsystem.config;
import com.example.coffeeshopmanagementsystem.mapper.CustomerMapper;
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
}
