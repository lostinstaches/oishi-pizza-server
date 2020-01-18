package com.underwoodkats.oishipizza.repositories;

import com.underwoodkats.oishipizza.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
