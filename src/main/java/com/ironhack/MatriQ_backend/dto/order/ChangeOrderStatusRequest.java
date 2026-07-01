package com.ironhack.MatriQ_backend.dto.order;

import com.ironhack.MatriQ_backend.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStatusRequest(@NotNull OrderStatus status) {
}
