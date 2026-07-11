package com.ironhack.MatriQ_backend.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateOrderRequest(
        @NotNull(message = "Listing id id required")
        UUID listingId,

        @Positive
        @NotNull
        @Min(value = 1, message = "Quantity should be at least 1")
        Integer quantity) {
}
