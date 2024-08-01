package com.example.coffeeshopmanagementsystem.service.stripe;

import com.example.coffeeshopmanagementsystem.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeWebhookServiceImpl {
    private final PaymentRepository paymentRepository;
}
