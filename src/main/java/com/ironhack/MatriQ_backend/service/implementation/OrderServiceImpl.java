package com.ironhack.MatriQ_backend.service.implementation;

import com.ironhack.MatriQ_backend.dto.order.ChangeOrderStatusRequest;
import com.ironhack.MatriQ_backend.dto.order.CreateOrderRequest;
import com.ironhack.MatriQ_backend.dto.order.OrderResponse;
import com.ironhack.MatriQ_backend.entity.Order;
import com.ironhack.MatriQ_backend.entity.User;
import com.ironhack.MatriQ_backend.entity.Listing;
import com.ironhack.MatriQ_backend.enums.OrderStatus;
import com.ironhack.MatriQ_backend.exception.InsufficientStockException;
import com.ironhack.MatriQ_backend.exception.InvalidStatusUpdateException;
import com.ironhack.MatriQ_backend.exception.ResourceNotFoundException;
import com.ironhack.MatriQ_backend.mapper.OrderMapper;
import com.ironhack.MatriQ_backend.repository.OrderRepository;
import com.ironhack.MatriQ_backend.repository.UserRepository;
import com.ironhack.MatriQ_backend.repository.ListingRepository;
import com.ironhack.MatriQ_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ListingRepository listingRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, UUID buyerId) {
        User user = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Listing listing = listingRepository.findById(request.listingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        if (listing.getStockQuantity() < request.quantity()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        BigDecimal unitPrice = listing.getPrice();

        BigDecimal discountRate = BigDecimal.ZERO;
        if (request.quantity() >= 500) {
            discountRate = new BigDecimal("0.10");
        } else if (request.quantity() >= 100) {
            discountRate = new BigDecimal("0.05");
        }

        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(request.quantity()));
        BigDecimal totalPrice = subtotal.subtract(subtotal.multiply(discountRate));

        listing.setStockQuantity(listing.getStockQuantity() - request.quantity());
        listingRepository.save(listing);

        Order order = new Order();
        order.setBuyer(user);
        order.setListing(listing);
        order.setQuantity(request.quantity());
        order.setUnitPrice(unitPrice);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);

        Order saved = orderRepository.save(order);
        return orderMapper.toResponseDTO(saved);
    }

    @Override
    public OrderResponse getOrderById(UUID id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return orderMapper.toResponseDTO(order);
    }

    @Override
    public Page<OrderResponse> getOrders(OrderStatus status, Pageable pageable){
        Page<Order> orders = (status != null)
                ? orderRepository.findByStatus(status, pageable)
                : orderRepository.findAll(pageable);

        return orders.map(orderMapper::toResponseDTO);
    }

    @Override
    public Page<OrderResponse> getOrdersByBuyerId(UUID buyerId, Pageable pageable){
        User user = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<Order> orders = orderRepository.findByBuyerId(buyerId, pageable);

        return orders.map(orderMapper::toResponseDTO);
    }

    private void validateTransition(OrderStatus current, OrderStatus next) {
        boolean isValid = switch (current) {
            case PENDING -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;
            case CONFIRMED -> next == OrderStatus.SHIPPED || next == OrderStatus.CANCELLED;
            case SHIPPED -> next == OrderStatus.COMPLETED;
            case COMPLETED -> false; // terminal, nothing allowed after
            case CANCELLED -> false; // terminal, nothing allowed after
        };

        if (!isValid) {
            throw new InvalidStatusUpdateException(
                    "Cannot change order status from " + current + " to " + next);
        }
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(UUID id, ChangeOrderStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus = request.status();

        validateTransition(currentStatus, newStatus);

        if (newStatus == OrderStatus.CANCELLED) {
            Listing listing = listingRepository.findById(order.getListing().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
            listing.setStockQuantity(listing.getStockQuantity() + order.getQuantity());
            listingRepository.save(listing);
        }

        order.setStatus(newStatus);
        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(UUID id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        orderRepository.delete(order);
    }

}
