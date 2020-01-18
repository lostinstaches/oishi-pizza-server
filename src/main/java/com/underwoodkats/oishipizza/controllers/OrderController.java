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

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

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

    @PostMapping(
            value = "/save",
            consumes = {"application/json"})
    public HttpEntity saveOrder(@RequestBody OrderRequest orderRequest) {

        Set<Integer> itemsIDs = orderRequest.getItemsAm ount().keySet();
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
}
