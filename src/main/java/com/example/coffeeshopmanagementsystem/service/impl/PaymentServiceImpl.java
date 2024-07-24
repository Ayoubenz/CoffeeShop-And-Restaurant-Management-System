package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.PaymentDto.CreatePaymentDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto.GetPaymentDto;
import com.example.coffeeshopmanagementsystem.entity.Order;
import com.example.coffeeshopmanagementsystem.entity.Payment;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
import com.example.coffeeshopmanagementsystem.entity.enums.PaymentStatus;
import com.example.coffeeshopmanagementsystem.exception.entities.ServiceException;
import com.example.coffeeshopmanagementsystem.mapper.PaymentMapper;
import com.example.coffeeshopmanagementsystem.repository.OrderRepository;
import com.example.coffeeshopmanagementsystem.repository.PaymentRepository;
import com.example.coffeeshopmanagementsystem.service.facade.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Synchronized
    @Override
    public GetPaymentDto addPayment(Long orderId, CreatePaymentDto createPaymentDto){

        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Check if the order is already paid
        if (order.getStatus() == OrderStatus.PAID) {
            throw new ServiceException("This order is paid!");
        }

        // Calculate total paid and find existing PENDING payment
        double totalPaid = calculateTotalPaid(order.getId());

        if (totalPaid == order.getTotalPrice()) {
            throw new ServiceException("The total payments are equal to the order total: Order paid");
        }

        //Verify if the payment amount is less or equals to the remaining amount of the order
        if(totalPaid < order.getTotalPrice() && (totalPaid + createPaymentDto.getAmount()) > order.getTotalPrice()){
            throw new ServiceException("The payment amount surpasses the order amount to be paid");
        }

        boolean hasPendingPayment = false;
        Payment pendingPayment = null;

        for (Payment payment : paymentRepository.findByOrderId(order.getId())) {
            if (payment.getPaymentStatus() == PaymentStatus.PENDING) {
                hasPendingPayment = true;
                pendingPayment = payment;
                break; // Exit early since we found a pending payment
            }
        }

        // Create or update the payment
        Payment newPayment = hasPendingPayment ? pendingPayment : new Payment();
        newPayment.setAmount(createPaymentDto.getAmount());
        newPayment.setOrderDateAndTime(LocalDateTime.now());
        newPayment.setPaymentStatus(PaymentStatus.COMPLETED);
        newPayment.setPaymentMethod(createPaymentDto.getPaymentMethod());
        newPayment.setOrder(order);

        // Save the payment
        Payment savedNewPayment = paymentRepository.save(newPayment);

        //update the payments in the order
        Set<Payment> orderPayments = order.getPayments();
        orderPayments.add(savedNewPayment);
        order.setPayments(orderPayments);

        //Verify if the order is fully or partially paid
        double totalPaidNow = calculateTotalPaid(order.getId());
        if(totalPaidNow < order.getTotalPrice()){
            order.setStatus(OrderStatus.PARTIALLY_PAID);
        }else{
            order.setStatus(OrderStatus.PAID);
        }
        orderRepository.save(order);

        return paymentMapper.toGetDto(savedNewPayment);
    }
    @Override
    public List<GetPaymentDto> getPaymentsByOrderId(Long id) {

        Order foundOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return paymentRepository.findByOrderId(foundOrder.getId())
                .stream()
                .map(paymentMapper::toGetDto)
                .toList();
    }


    //Utility methods
    private double calculateTotalPaid(Long orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .filter(payment -> payment.getPaymentStatus() == PaymentStatus.COMPLETED)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

}
