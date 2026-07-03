package com.ironhack.MatriQ_backend.controller;

import com.ironhack.MatriQ_backend.dto.review.ReviewCreateDto;
import com.ironhack.MatriQ_backend.dto.review.ReviewResponseDto;
import com.ironhack.MatriQ_backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/listings/{id}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getReviewsByListing(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewsByListing(id, pageable));
    }

    @PostMapping("/listings/{id}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable UUID id, @Valid @RequestBody ReviewCreateDto dto) {
        return new ResponseEntity<>(reviewService.createReview(id, dto), HttpStatus.CREATED);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable UUID id, @Valid @RequestBody ReviewCreateDto dto) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}