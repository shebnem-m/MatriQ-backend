package com.ironhack.MatriQ_backend.mapper;

import com.ironhack.MatriQ_backend.dto.order.OrderResponse;
import com.ironhack.MatriQ_backend.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toResponseDTO(Order order) {
        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getBuyer().getId(),
                order.getListing().getId(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
                );
        return response;
    }
}
