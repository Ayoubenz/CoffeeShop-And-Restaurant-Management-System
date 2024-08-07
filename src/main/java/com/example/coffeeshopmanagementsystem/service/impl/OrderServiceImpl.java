package com.example.coffeeshopmanagementsystem.service.impl;

import com.example.coffeeshopmanagementsystem.dto.OrderDto.GetOrderDto;
import com.example.coffeeshopmanagementsystem.dto.OrderDto.OrderPlacementDto;
import com.example.coffeeshopmanagementsystem.dto.OrderItemDto;
import com.example.coffeeshopmanagementsystem.entity.*;
import com.example.coffeeshopmanagementsystem.entity.enums.OrderStatus;
import com.example.coffeeshopmanagementsystem.entity.enums.PaymentMethod;
import com.example.coffeeshopmanagementsystem.entity.enums.PaymentStatus;
import com.example.coffeeshopmanagementsystem.mapper.OrderMapper;
import com.example.coffeeshopmanagementsystem.repository.*;
import com.example.coffeeshopmanagementsystem.service.facade.OrderService;
import com.example.coffeeshopmanagementsystem.service.stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    private final StripeService stripeService;


    //In application order by an employee
    @Override
    @Transactional
    public GetOrderDto placeOrderInShop(OrderPlacementDto orderPlacementDto){

        // Check if the customer exists
        Customer customer = findCustomerById(orderPlacementDto.getCustomerId());

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


        savedOrder.setPayments(payments);

        return orderMapper.toGetDto(savedOrder);
    }

    //Online ordering by customers + online payment with Stripe
    @Override
    @Transactional
    public GetOrderDto placeOrderOnline(OrderPlacementDto orderPlacementDto) throws StripeException {
        // Check if the customer exists
        Customer customer = findCustomerById(orderPlacementDto.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        Set<OrderItem> orderItems = new HashSet<>();
        double totalPrice = 0.0;

        for (OrderItemDto orderItemDto : orderPlacementDto.getOrderItems()) {
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

        // Create Checkout Session using Stripe
        Session session = stripeService.createCheckoutSession(totalPrice);


        // Create and save payments using Stripe
        PaymentIntent paymentIntent = stripeService.createPaymentIntent(totalPrice);
        Payment payment = new Payment();
        payment.setAmount(totalPrice);
        payment.setOrder(savedOrder);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setOrderDateAndTime(LocalDateTime.now());
        payment.setStripePaymentIntentId(paymentIntent.getId());
        Payment savedPayment = paymentRepository.save(payment);

        savedOrder.setPayments(Set.of(savedPayment));

        return orderMapper.toGetDto(savedOrder);
    }


        //Crud operations
    @Override
    public GetOrderDto getOrderById(Long id){
        return orderRepository
                .findById(id)
                .map(orderMapper::toGetDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public List<GetOrderDto> getOrdersByCustomerId(Long id){
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return orderRepository
                .findByCustomerId(foundCustomer.getId())
                .stream()
                .map(orderMapper::toGetDto)
                .toList();
    }

    @Override
    public List<GetOrderDto> getOrderByStatus(OrderStatus status){
        return orderRepository
                .findByStatus(status)
                .stream()
                .map(orderMapper::toGetDto)
                .toList();
    }

    @Override
    public GetOrderDto getOrderByPaymentId(Long id){
        Payment payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return orderRepository
                .findOrderByPaymentId(id)
                .map(orderMapper::toGetDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

        @Override
    public List<GetOrderDto> getAllOrders(){
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toGetDto)
                .toList();
    }

    @Override
    public void deleteOrder(Long id){
        if(!orderRepository.existsById(id)){
            throw new EntityNotFoundException("Order you want to delete doesn't exist");
        }
        orderRepository.deleteById(id);
    }

    //Utility method - Checking if a customer exists
    private Customer findCustomerById(Long id){
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No customer found"));
    }
}
