package com.ironhack.MatriQ_backend.repositories;

import com.ironhack.MatriQ_backend.entities.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ListingRepository extends JpaRepository<Listing, UUID> {

    List<Listing> findBySupplierId(UUID supplierId);

    List<Listing> findByCategory(String category);

    List<Listing> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Listing> findByTitleContainingIgnoreCase(String title);
}