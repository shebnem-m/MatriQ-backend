package com.ironhack.MatriQ_backend.repository;

import com.ironhack.MatriQ_backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Page<Review> findByListingId(UUID listingId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.listingId = :listingId")
    Double findAverageRatingByListingId(@Param("listingId") UUID listingId);
}