package com.ironhack.MatriQ_backend.repository;


import com.ironhack.MatriQ_backend.entity.Order;
import com.ironhack.MatriQ_backend.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByBuyerId(Long buyerId);
    List<Order> findByListingId(Long listingId);
    List<Order> findByStatus(OrderStatus status);
}
