package com.underwoodkats.oishipizza.repositories;

import com.underwoodkats.oishipizza.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository allow us to make operations with Orders table.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
