package com.ironhack.MatriQ_backend.mappers;

import com.ironhack.MatriQ_backend.dto.listing.ListingCreateDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingResponseDto;
import com.ironhack.MatriQ_backend.entity.Listing;
import org.springframework.stereotype.Component;

@Component
public class ListingMapper {

    public ListingResponseDto toResponseDto(Listing listing) {
        if (listing == null) return null;

        return ListingResponseDto.builder()
                .id(listing.getId())
                .supplierId(listing.getSupplierId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .category(listing.getCategory())
                .materialType(listing.getMaterialType())
                .unit(listing.getUnit())
                .price(listing.getPrice())
                .stockQuantity(listing.getStockQuantity())
                .deliveryDays(listing.getDeliveryDays())
                .createdAt(listing.getCreatedAt())
                .updatedAt(listing.getUpdatedAt())
                .build();
    }

    public Listing toEntity(ListingCreateDto dto) {
        if (dto == null) return null;

        return Listing.builder()
                .supplierId(dto.getSupplierId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .materialType(dto.getMaterialType())
                .unit(dto.getUnit())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .deliveryDays(dto.getDeliveryDays())
                .build();
    }
}