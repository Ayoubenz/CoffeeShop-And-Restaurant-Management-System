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
    public GetPaymentDto getPaymentById(Long id){
        return paymentRepository
                .findById(id)
                .map(paymentMapper::toGetDto)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
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

    @Override
    public List<GetPaymentDto> getAllpayments(){
        return paymentRepository
                .findAll()
                .stream()
                .map(paymentMapper::toGetDto)
                .toList();
    }

    @Override
    @Transactional
    public GetPaymentDto updatePayment(Long id, CreatePaymentDto createPaymentDto){

        try {
            Payment existingPayment = paymentRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

            //Find the order
            Order order = orderRepository
                    .findOrderByPaymentId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found"));

            // Calculate the total paid amount excluding the existing payment
            double totalPaid = calculateTotalPaid(order.getId()) - existingPayment.getAmount();

            // Calculate the remaining amount to be paid
            double orderAmount = order.getTotalPrice();
            double remainingAmount = orderAmount - totalPaid;

            //verify if the updated amount is bigger than the order remaining amount
            if(createPaymentDto.getAmount() >remainingAmount){
                throw new ServiceException("The payment amount is larger than the amount to be paid : "+ remainingAmount);
            }

            //update the payment
            existingPayment.setAmount(createPaymentDto.getAmount());
            existingPayment.setOrderDateAndTime(createPaymentDto.getOrderDateAndTime());
            existingPayment.setPaymentStatus(createPaymentDto.getPaymentStatus());
            existingPayment.setPaymentMethod(createPaymentDto.getPaymentMethod());

            //Save the updated amount
            Payment updatedPayment = paymentRepository.save(existingPayment);

            //updating the order status after editing the payment
            if(updatedPayment.getAmount() == remainingAmount){
                order.setStatus(OrderStatus.PAID);
            }else{
                order.setStatus(OrderStatus.PARTIALLY_PAID);
            }
            //update the order
            orderRepository.save(order);

            return paymentMapper.toGetDto(updatedPayment);
        }catch (Exception e){
            throw new ServiceException("Failed to update the payment with id " + id + ": " + e.getMessage(), e);

        }
    }

    @Override
    public void deletePayment(Long id){
        if(!paymentRepository.existsById(id)){
            throw new ServiceException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }


    //Utility methods
    private double calculateTotalPaid(Long orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .filter(payment -> payment.getPaymentStatus() == PaymentStatus.COMPLETED)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

}
