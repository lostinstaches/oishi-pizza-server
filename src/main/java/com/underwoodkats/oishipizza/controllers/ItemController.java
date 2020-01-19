package com.underwoodkats.oishipizza.controllers;

import com.underwoodkats.oishipizza.models.Item;
import com.underwoodkats.oishipizza.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller provide endpoints to process data about items.
 */
@Controller
@RequestMapping(path = "/menu")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * This endpoint returns List of all items that we store.
     * @return ResponseEntity<List<Item>>
     */
    @GetMapping(path = "/all")
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemRepository.findAll(), HttpStatus.OK);
    }

    /**
     * This endpoint let the party save a new item.
     * @return HttpEntity
     */
    @PostMapping(path = "/save")
    public HttpEntity saveItem(@RequestBody Item item) {
        if (item != null) {
            if (item.getTitle() == null ||
                    item.getPriceDollar() == null ||
                    item.getType() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete information!");
            } else {
                itemRepository.save(item);
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete information!");
        }
    }

    /**
     * This endpoint delete all items that we store.
     * @return HttpEntity
     */
    @DeleteMapping(value = "/delete")
    public HttpEntity deleteAllItems() {
        itemRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * This endpoint delete one specific item by its id.
     * @param id - identification of the item that has to be deleted.
     * @return HttpEntity
     */
    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity deleteAllItems(@PathVariable int id) {
        itemRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
