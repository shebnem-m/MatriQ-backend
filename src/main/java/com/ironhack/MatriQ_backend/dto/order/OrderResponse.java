package com.ironhack.MatriQ_backend.dto.order;

import com.ironhack.MatriQ_backend.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID buyerId,
        UUID listingId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
