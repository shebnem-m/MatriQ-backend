package com.ironhack.MatriQ_backend.dto.listing;

import com.ironhack.MatriQ_backend.enums.MaterialType;
import com.ironhack.MatriQ_backend.enums.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingCreateDto {

    @NotNull(message = "Supplier ID cannot be null")
    private UUID supplierId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Material type cannot be null")
    private MaterialType materialType;

    @NotNull(message = "Unit cannot be null")
    private Unit unit;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Stock quantity cannot be null")
    @Positive(message = "Stock quantity must be positive")
    private Integer stockQuantity;

    @NotNull(message = "Delivery days cannot be null")
    @Positive(message = "Delivery days must be positive")
    private Integer deliveryDays;
}