package com.underwoodkats.oishipizza.services;

import com.underwoodkats.oishipizza.models.*;
import com.underwoodkats.oishipizza.repositories.ItemRepository;
import com.underwoodkats.oishipizza.repositories.OrderItemRepository;
import com.underwoodkats.oishipizza.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * This service provide business logic with orders.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<OrderItem> getAllOrderItemRelations() {
        return orderItemRepository.findAll();
    }

    /**
     * This method returns all orders from the repositories with some preparations.
     *
     * @return
     */
    public List<OrderClient> getAllOrders() {

        List<Order> ordersFromRepository = orderRepository.findAll();
        List<OrderItem> orderItemsFromRepository = orderItemRepository.findAll();

        Map<Integer, OrderClient> transitionalMapIdOrder = createTransitionalMapIdOrder(ordersFromRepository);

        return prepareListOfOrdersForClient(orderItemsFromRepository, transitionalMapIdOrder);
    }

    /**
     * This method saves order to the repository if all elements in that order are valid.
     *
     * @param orderRequest - order in form that client send in order to save this information.
     * @return true if order has been saved and false otherwise.
     */
    public boolean saveOrderIfOrderValid(OrderRequest orderRequest) {
        if (checkOrderRequest(orderRequest)) {
            Set<Integer> itemsIDs = orderRequest.getItemsAmount().keySet();
            try {
                OrderItem[] orderItemsArray = createOrderItemRelationArrayFromItemsIdsInRequest(itemsIDs, orderRequest);
                Order order = new Order(orderRequest.getCustomerName(), orderRequest.getAddress(), orderRequest.getPhoneNumber(),
                        orderRequest.getComment(), orderRequest.getTotalItemPriceDollar(),
                        orderRequest.getDeliveryPriceDollar(), orderItemsArray);
                orderRepository.save(order);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * This is transitional method that create Array of relations between orders and items.
     *
     * @param itemsIDs     - items ids that been passed with order request.
     * @param orderRequest - order request that been received from the client part.
     * @return OrderItem[]
     * @throws Exception in case if we can't find at least one item in the repository. In this case we can't proceed
     * an order.
     */
    private OrderItem[] createOrderItemRelationArrayFromItemsIdsInRequest(Set<Integer> itemsIDs,
                                                                          OrderRequest orderRequest) throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();

        for (Integer itemID : itemsIDs) {

            Optional<Item> optionalItem = itemRepository.findById(itemID);
            if (!optionalItem.isPresent()) {
                throw new Exception("There is no such item in stock.");
            }
            Item item = optionalItem.get();
            orderItems.add(
                    new OrderItem(
                            item, orderRequest.getItemsAmount().get(itemID)));
        }

        OrderItem[] orderItemsArray = orderItems.toArray(new OrderItem[orderItems.size()]);
        return orderItemsArray;

    }

    /**
     * In order to return all orders to the user we first need to proceed the information that we store in our
     * repositories. This function is transitional and make a map for us the has as a key order id and as a value
     * order that will be returned to the client side.
     *
     * @param orders - list of orders that we retrieve from repository and pass to this function.
     * @return Map<Integer, OrderClient>
     */
    private Map<Integer, OrderClient> createTransitionalMapIdOrder(List<Order> orders) {

        Map<Integer, OrderClient> transitionalMapIdOrder = new HashMap<>();
        for (Order order : orders) {
            Map<String, Integer> amount = new HashMap<>();

            OrderClient orderClient = new OrderClient(order.getId(), order.getCustomerName(), order.getAddress(), order.getPhoneNumber(),
                    order.getComment(), order.getTotalItemPriceDollar(), order.getDeliveryPriceDollar(),
                    order.getCreatedDate(), amount);
            transitionalMapIdOrder.put(order.getId(), orderClient);
        }
        return transitionalMapIdOrder;
    }

    /**
     * In order to return all orders to the user we first need to proceed the information that we store in our
     * repositories. This function create the final list of Orders that are in suitable form for the client.
     *
     * @param orderItemsFromRepository - all entities of relations between order and items
     * @param transitionalMapIdOrder   - transitional map that was prepared on the previous step.
     * @return List<OrderClient>
     */
    private List<OrderClient> prepareListOfOrdersForClient(List<OrderItem> orderItemsFromRepository,
                                                           Map<Integer, OrderClient> transitionalMapIdOrder) {
        for (OrderItem orderItem : orderItemsFromRepository) {
            int orderID = orderItem.getOrder().getId();
            OrderClient orderClientFromMap = transitionalMapIdOrder.get(orderID);
            Map<String, Integer> itemsAmountMap = orderClientFromMap.getItems();
            itemsAmountMap.put(
                    orderItem.getItem().getTitle(), orderItem.getAmount());
            orderClientFromMap.setItems(itemsAmountMap);
            transitionalMapIdOrder.replace(orderID, orderClientFromMap);
        }

        List<OrderClient> orderClientList;
        orderClientList = new ArrayList<>(transitionalMapIdOrder.values());
        return orderClientList;
    }

    /**
     * This method checks fields of the order that should be present
     * in order to save the entity.
     *
     * @param orderRequest - order in form that client send in order to save this information.
     * @return true if order is valid and false if it is not.
     */
    private boolean checkOrderRequest(OrderRequest orderRequest) {
        if (orderRequest != null) {
            return orderRequest.getCustomerName() != null &&
                    orderRequest.getAddress() != null &&
                    orderRequest.getPhoneNumber() != null &&
                    orderRequest.getTotalItemPriceDollar() != null &&
                    orderRequest.getDeliveryPriceDollar() != null &&
                    orderRequest.getItemsAmount() != null;
        }
        return false;
    }
}
