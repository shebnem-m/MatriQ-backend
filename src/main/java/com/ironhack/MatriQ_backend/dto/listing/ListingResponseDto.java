package com.ironhack.MatriQ_backend.dto.listing;

import com.ironhack.MatriQ_backend.enums.MaterialType;
import com.ironhack.MatriQ_backend.enums.Unit;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingResponseDto {
    private UUID id;
    private UUID supplierId;
    private String imageUrl;
    private String title;
    private String description;
    private String category;
    private MaterialType materialType;
    private Unit unit;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer deliveryDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}