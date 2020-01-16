package com.underwoodkats.oishipizza.repositories;

import com.underwoodkats.oishipizza.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
