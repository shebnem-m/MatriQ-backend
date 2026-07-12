package com.ironhack.MatriQ_backend.repository;

import com.ironhack.MatriQ_backend.entity.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;


@Repository
public interface ListingRepository extends JpaRepository<Listing, UUID>, JpaSpecificationExecutor<Listing> {


    Page<Listing> findBySupplierId(UUID supplierId, Pageable pageable);

    Page<Listing> findByCategory(String category, Pageable pageable);

    Page<Listing> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Listing> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}