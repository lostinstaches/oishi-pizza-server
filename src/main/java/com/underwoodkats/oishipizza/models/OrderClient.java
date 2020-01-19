package com.underwoodkats.oishipizza.models;

import lombok.Data;

import java.util.Date;
import java.util.Map;


/**
 * This class descries an object that server sends to the
 * party that requests information about the order.
 */
@Data
public class OrderClient {

    private int id;
    private String customerName;
    private String address;
    private String phoneNumber;
    private String comment;
    private Double totalItemPriceDollar;
    private Double deliveryPriceDollar;
    private Date createdDate;
    private Map<String, Integer> items;


    public OrderClient(int id, String customerName, String address, String phoneNumber, String comment,
                       Double totalItemPriceDollar, Double deliveryPriceDollar, Date createdDate, Map<String, Integer> items) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
        this.totalItemPriceDollar = totalItemPriceDollar;
        this.deliveryPriceDollar = deliveryPriceDollar;
        this.createdDate = createdDate;
        this.items = items;
    }
}
