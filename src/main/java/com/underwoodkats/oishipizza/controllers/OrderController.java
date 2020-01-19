package com.underwoodkats.oishipizza.controllers;

import com.underwoodkats.oishipizza.models.OrderClient;
import com.underwoodkats.oishipizza.models.OrderItem;
import com.underwoodkats.oishipizza.models.OrderRequest;
import com.underwoodkats.oishipizza.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller provide endpoints to process data about order.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * This endpoint returns the list of all orders in a suitable form for the client.
     *
     * @return ResponseEntity<List < OrderClient>>
     */
    @GetMapping(path = "/history")
    public ResponseEntity<List<OrderClient>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    /**
     * This endpoint lets the party save a new order.
     *
     * @param orderRequest - object that contains all necessary information to proceed the order
     *                     and save it.
     * @return HttpEntity
     */
    @PostMapping(
            value = "/save",
            consumes = {"application/json"})
    public HttpEntity saveOrder(@RequestBody OrderRequest orderRequest) {

        if (orderService.saveOrderIfOrderValid(orderRequest)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request!");
        }
    }

    /**
     * This endpoint returns the list of all relations between orders and items.
     *
     * @return ResponseEntity<List < OrderItem>>
     */
    @GetMapping(path = "/order-items")
    public ResponseEntity<List<OrderItem>> getAllOrderItemRelations() {
        return new ResponseEntity<>(orderService.getAllOrderItemRelations(), HttpStatus.OK);
    }
}
