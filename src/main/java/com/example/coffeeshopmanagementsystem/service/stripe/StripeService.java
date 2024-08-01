package com.example.coffeeshopmanagementsystem.service.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;

public interface StripeService {
    Session createCheckoutSession(double amount) throws StripeException;
    PaymentIntent createPaymentIntent(double amount) throws StripeException;
}
