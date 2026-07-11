package com.ironhack.MatriQ_backend.controller;

import com.ironhack.MatriQ_backend.dto.order.ChangeOrderStatusRequest;
import com.ironhack.MatriQ_backend.dto.order.CreateOrderRequest;
import com.ironhack.MatriQ_backend.dto.order.OrderResponse;
import com.ironhack.MatriQ_backend.enums.OrderStatus;
import com.ironhack.MatriQ_backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @RequestParam UUID buyerId) {

        OrderResponse response = orderService.createOrder(request, buyerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER', 'CUSTOMER')")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER')")
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @RequestParam(required = false) OrderStatus status, Pageable pageable) {

        return ResponseEntity.ok(orderService.getOrders(status, pageable));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER')")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody ChangeOrderStatusRequest request) {

        return ResponseEntity.ok(orderService.updateStatus(id, request));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER', 'CUSTOMER')")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable UUID id){
        return ResponseEntity.ok().body(orderService.cancelOrder(id));
    }
}