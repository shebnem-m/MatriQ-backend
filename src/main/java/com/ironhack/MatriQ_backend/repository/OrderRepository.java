package com.ironhack.MatriQ_backend.repository;


import com.ironhack.MatriQ_backend.entity.Order;
import com.ironhack.MatriQ_backend.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByBuyerId(UUID buyerId, Pageable pageable);
    Page<Order> findByListingId(UUID listingId, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
}
