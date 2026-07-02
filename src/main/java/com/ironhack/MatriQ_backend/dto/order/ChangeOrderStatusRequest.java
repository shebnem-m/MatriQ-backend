package com.ironhack.MatriQ_backend.dto.order;

import com.ironhack.MatriQ_backend.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStatusRequest(@NotNull OrderStatus status) {
}
