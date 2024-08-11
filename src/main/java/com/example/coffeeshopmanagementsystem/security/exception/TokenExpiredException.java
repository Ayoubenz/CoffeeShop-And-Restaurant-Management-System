package com.example.coffeeshopmanagementsystem.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException(String message) {
        super(message);
    }
}
