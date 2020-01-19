package com.underwoodkats.oishipizza.services;

import com.underwoodkats.oishipizza.models.Item;
import com.underwoodkats.oishipizza.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service provide business logic with items.
 * Some of the methods looks simple but this way to structure the project
 * protect from the poorly readable code.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }

    public void deleteItemById(int id) {
        itemRepository.deleteById(id);
    }

    /**
     * This method save item in the repository if item is valid.
     *
     * @param item - element that will be proceed
     * @return true if item has been saved and false otherwise.
     */
    public boolean saveItemIfItemValid(Item item) {
        if (isItemValid(item)) {
            itemRepository.save(item);
            return true;
        }
        return false;
    }

    /**
     * This method check fields of the item that should be present
     * in order to save the item.
     *
     * @param item - element that will be proceed
     * @return true if item is valid and false if it is not.
     */
    private boolean isItemValid(Item item) {
        if (item != null) {
            return item.getTitle() != null &&
                    item.getPriceDollar() != null &&
                    item.getType() != null;
        }
        return false;
    }
}
