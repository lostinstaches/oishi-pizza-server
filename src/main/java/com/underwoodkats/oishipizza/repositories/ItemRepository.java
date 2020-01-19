package com.underwoodkats.oishipizza.repositories;

import com.underwoodkats.oishipizza.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository allow us to make operations with Items table.
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findById(int id);
}
