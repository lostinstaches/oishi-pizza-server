package com.underwoodkats.oishipizza.models;

import lombok.Data;

import java.util.Date;
import java.util.Map;

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
