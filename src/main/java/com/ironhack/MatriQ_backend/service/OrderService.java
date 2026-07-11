package com.ironhack.MatriQ_backend.service;

import com.ironhack.MatriQ_backend.dto.order.ChangeOrderStatusRequest;
import com.ironhack.MatriQ_backend.dto.order.CreateOrderRequest;
import com.ironhack.MatriQ_backend.dto.order.OrderResponse;
import com.ironhack.MatriQ_backend.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request, UUID buyerId);
    OrderResponse getOrderById(UUID id);
    Page<OrderResponse> getOrders(OrderStatus status, Pageable pageable);
    Page<OrderResponse> getOrdersByBuyerId(UUID buyerId, Pageable pageable);
    OrderResponse updateStatus(UUID id, ChangeOrderStatusRequest request);
    void deleteOrder(UUID id);
    OrderResponse cancelOrder(UUID id);
}
