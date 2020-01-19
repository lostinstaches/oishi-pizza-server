package com.underwoodkats.oishipizza.repositories;

import com.underwoodkats.oishipizza.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository allow us to make operations with OrderItems table.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
