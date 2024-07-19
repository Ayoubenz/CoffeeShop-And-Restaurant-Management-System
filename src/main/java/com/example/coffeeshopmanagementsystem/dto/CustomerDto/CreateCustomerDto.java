package com.example.coffeeshopmanagementsystem.dto.CustomerDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCustomerDto {
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
    private String name;

}
