package com.underwoodkats.oishipizza.controllers;

import com.underwoodkats.oishipizza.models.*;
import com.underwoodkats.oishipizza.repositories.ItemRepository;
import com.underwoodkats.oishipizza.repositories.OrderItemRepository;
import com.underwoodkats.oishipizza.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * This controller provide endpoints to process data about order.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * This endpoint returns the list of all orders in a suitable form for the client.
     * @return ResponseEntity<List<OrderClient>>
     */
    @GetMapping(path = "/history")
    public ResponseEntity<List<OrderClient>> getAllOrders() {

        List<OrderClient> orderClientList;

        List<Order> ordersFromRepository = orderRepository.findAll();

        Map<Integer, OrderClient> ordersFromRepositoryMap = new HashMap<>();
        for (Order order : ordersFromRepository) {
            Map<String, Integer> amount = new HashMap<>();

            OrderClient orderClient = new OrderClient(order.getId(), order.getCustomerName(), order.getAddress(), order.getPhoneNumber(),
                    order.getComment(), order.getTotalItemPriceDollar(), order.getDeliveryPriceDollar(),
                    order.getCreatedDate(), amount);
            ordersFromRepositoryMap.put(order.getId(), orderClient);
        }

        List<OrderItem> allOrderItems = orderItemRepository.findAll();
        for (OrderItem orderItem : allOrderItems) {
            int orderID = orderItem.getOrder().getId();
            OrderClient orderClientFromMap = ordersFromRepositoryMap.get(orderID);
            Map<String, Integer> itemsAmountMap = orderClientFromMap.getItems();
            itemsAmountMap.put(
                    orderItem.getItem().getTitle(), orderItem.getAmount());
            orderClientFromMap.setItems(itemsAmountMap);
            ordersFromRepositoryMap.replace(orderID, orderClientFromMap);
        }

        orderClientList = new ArrayList<>(ordersFromRepositoryMap.values());

        return new ResponseEntity<>(orderClientList, HttpStatus.OK);
    }

    /**
     * This endpoint lets the party save a new order.
     * @param orderRequest - object that contains all necessary information to proceed the order
     *                     and save it.
     * @return HttpEntity
     */
    @PostMapping(
            value = "/save",
            consumes = {"application/json"})
    public HttpEntity saveOrder(@RequestBody OrderRequest orderRequest) {

        Set<Integer> itemsIDs = orderRequest.getItemsAmount().keySet();
        List<OrderItem> orderItems = new ArrayList<>();

        for (Integer itemID : itemsIDs) {

            Optional<Item> optionalItem = itemRepository.findById(itemID);
            if (!optionalItem.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("We don't have this item in stock!");
            }
            Item item = optionalItem.get();
            orderItems.add(
                    new OrderItem(
                            item, orderRequest.getItemsAmount().get(itemID)));
        }

        OrderItem[] orderItemsArray =
                orderItems.toArray(new OrderItem[orderItems.size()]);
        Order order =
                new Order(orderRequest.getCustomerName(), orderRequest.getAddress(), orderRequest.getPhoneNumber(),
                        orderRequest.getComment(), orderRequest.getTotalItemPriceDollar(),
                        orderRequest.getDeliveryPriceDollar(),orderItemsArray);
        orderRepository.save(order);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * This endpoint returns the list of all relations between orders and items.
     * @return ResponseEntity<List<OrderItem>>
     */
    @GetMapping(path = "/order-items")
    public ResponseEntity<List<OrderItem>> getAllOrderItemRelations() {
        return new ResponseEntity<>(orderItemRepository.findAll(), HttpStatus.OK);
    }
}
