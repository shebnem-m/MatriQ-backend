package com.ironhack.MatriQ_backend.repositories;

import com.ironhack.MatriQ_backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByListingId(UUID listingId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.listingId = :listingId")
    Double findAverageRatingByListingId(@Param("listingId") UUID listingId);
}