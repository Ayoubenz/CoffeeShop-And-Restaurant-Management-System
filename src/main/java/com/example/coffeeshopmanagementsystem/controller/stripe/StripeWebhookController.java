package com.example.coffeeshopmanagementsystem.controller.stripe;

import com.example.coffeeshopmanagementsystem.entity.Payment;
import com.example.coffeeshopmanagementsystem.entity.enums.PaymentStatus;
import com.example.coffeeshopmanagementsystem.repository.PaymentRepository;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final PaymentRepository paymentRepository;

    @Value("${webhook.key}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        System.out.println("Received webhook event with payload: " + payload);
        System.out.println("Received Stripe-Signature header: " + sigHeader);

        try {
            // Verify and parse the webhook event
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            // Handle the event based on its type
            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
                Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntent.getId());
                if (payment != null) {
                    payment.setPaymentStatus(PaymentStatus.COMPLETED);
                    paymentRepository.save(payment);
                }
            }

            // Respond to Stripe to acknowledge receipt of the event
            return ResponseEntity.ok("Event received");
        } catch (Exception e) {
            // Handle errors (e.g., invalid signature)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event processing error");
        }
    }
}
