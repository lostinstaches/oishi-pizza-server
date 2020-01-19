package com.underwoodkats.oishipizza.models;

import lombok.Data;

import java.util.Map;


/**
 * This class descries an object that server receive from the
 * party that requests information about the order.
 *
 */
@Data
public class OrderRequest {

    private String customerName;
    private String address;
    private String phoneNumber;
    private String comment;
    private Double totalItemPriceDollar;
    private Double deliveryPriceDollar;
    private Map<Integer, Integer> itemsAmount;
}
