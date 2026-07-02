package com.ironhack.MatriQ_backend.services.implementation;

import com.ironhack.MatriQ_backend.dto.review.ReviewCreateDto;
import com.ironhack.MatriQ_backend.dto.review.ReviewResponseDto;
import com.ironhack.MatriQ_backend.entity.Review;
import com.ironhack.MatriQ_backend.mapper.ReviewMapper;
import com.ironhack.MatriQ_backend.repository.ReviewRepository;
import com.ironhack.MatriQ_backend.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public Page<ReviewResponseDto> getReviewsByListing(UUID listingId, Pageable pageable) {
        return reviewRepository.findByListingId(listingId, pageable)
                .map(reviewMapper::toResponseDto);
    }

    @Override
    public ReviewResponseDto createReview(UUID listingId, ReviewCreateDto dto) {
        Review review = reviewMapper.toEntity(dto, listingId);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toResponseDto(savedReview);
    }

    @Override
    public ReviewResponseDto updateReview(UUID id, ReviewCreateDto dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toResponseDto(updatedReview);
    }

    @Override
    public void deleteReview(UUID id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }


    public Double getAverageRatingForListing(UUID listingId) {
        Double averageRating = reviewRepository.findAverageRatingByListingId(listingId);
        return averageRating != null ? averageRating : 0.0;
    }
}