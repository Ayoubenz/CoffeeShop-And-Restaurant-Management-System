package com.example.coffeeshopmanagementsystem.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe-secret-key}")
    String stripeSecretKey;

    @PostConstruct //becasue bean should return an object - I want only to  set the API key for the Stripe library
    public void initStripe() {
        Stripe.apiKey = stripeSecretKey;
    }
}
