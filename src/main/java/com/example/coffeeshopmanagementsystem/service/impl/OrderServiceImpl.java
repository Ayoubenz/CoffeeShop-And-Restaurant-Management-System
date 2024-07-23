package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;
import com.example.coffeeshopmanagementsystem.dto.OrderItemDto;
import com.example.coffeeshopmanagementsystem.dto.PaymentDto;
import com.example.coffeeshopmanagementsystem.entity.*;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
import com.example.coffeeshopmanagementsystem.entity.enums.PaymentStatus;
import com.example.coffeeshopmanagementsystem.mapper.OrderMapper;
import com.example.coffeeshopmanagementsystem.repository.*;
import com.example.coffeeshopmanagementsystem.service.facade.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto placeOrderInShop(OrderPlacementDto orderPlacementDto){

        //Check if the customer exists
        Customer customer = customerRepository
                .findById(orderPlacementDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("No customer found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        Set<OrderItem> orderItems = new HashSet<>();
        double totalPrice = 0.0;

        for(OrderItemDto orderItemDto : orderPlacementDto.getOrderItems()){
            Product product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setOrderedPrice(orderItemDto.getOrderedPrice() != null ? orderItemDto.getOrderedPrice() : product.getPrice());
            orderItems.add(orderItem);
            totalPrice += orderItem.getQuantity() * orderItem.getOrderedPrice();
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Save order items
        orderItemRepository.saveAll(orderItems);

        // Create and save payments
        Set<Payment> payments = new HashSet<>();
            Payment payment = new Payment();
            payment.setAmount(totalPrice);
            payment.setOrder(savedOrder);
            payment.setPaymentStatus(PaymentStatus.PENDING);
            Payment savedPayment = paymentRepository.save(payment);
            payments.add(savedPayment);


        savedOrder.setPayment(payments);

        return orderMapper.toDto(savedOrder);
    }
}
