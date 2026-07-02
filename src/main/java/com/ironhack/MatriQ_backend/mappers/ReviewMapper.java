package com.ironhack.MatriQ_backend.mappers;

import com.ironhack.MatriQ_backend.dto.review.ReviewCreateDto;
import com.ironhack.MatriQ_backend.dto.review.ReviewResponseDto;
import com.ironhack.MatriQ_backend.entity.Review;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReviewMapper {

    public ReviewResponseDto toResponseDto(Review review) {
        if (review == null) return null;

        return ReviewResponseDto.builder()
                .id(review.getId())
                .listingId(review.getListingId())
                .ownerId(review.getOwnerId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public Review toEntity(ReviewCreateDto dto, UUID listingId) {
        if (dto == null) return null;

        return Review.builder()
                .listingId(listingId)
                .ownerId(dto.getOwnerId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();
    }
}