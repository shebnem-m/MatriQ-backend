package com.ironhack.MatriQ_backend.services;

import com.ironhack.MatriQ_backend.dto.review.ReviewCreateDto;
import com.ironhack.MatriQ_backend.dto.review.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ReviewService {
    Page<ReviewResponseDto> getReviewsByListing(UUID listingId, Pageable pageable);
    ReviewResponseDto createReview(UUID listingId, ReviewCreateDto dto);
    ReviewResponseDto updateReview(UUID id, ReviewCreateDto dto);
    void deleteReview(UUID id);
}