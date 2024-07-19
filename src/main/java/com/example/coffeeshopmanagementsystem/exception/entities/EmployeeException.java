package com.example.coffeeshopmanagementsystem.exception.entities;

public class EmployeeException extends RuntimeException{
    public EmployeeException(String message) {
        super(message);
    }

    public EmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
