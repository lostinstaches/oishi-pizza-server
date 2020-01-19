package com.underwoodkats.oishipizza.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This entity describes an Order object.
 * This entity has many to many relation with entity Item.
 * This entity contain the information that is necessary for the delivery person.
 *
 */
@Data
@Entity(name="Orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "orderItems"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "customerAddress")
    private String address;

    @Column(name = "customerPhoneNumber")
    private String phoneNumber;

    @Column(name = "orderComment")
    private String comment;

    @Column(name = "totalItemPriceDollar")
    private Double totalItemPriceDollar;

    @Column(name = "deliveryPriceDollar")
    private Double deliveryPriceDollar;

    @CreatedDate
    @Column(name = "orderCreatedDate")
    private Date createdDate;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order(String customerName, String address, String phoneNumber, String comment, Double totalItemPriceDollar,
                 Double deliveryPriceDollar, OrderItem... orderItems) {
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
        this.totalItemPriceDollar = totalItemPriceDollar;
        this.deliveryPriceDollar = deliveryPriceDollar;
        this.createdDate = new Date();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(this);
        }
        this.orderItems = Stream.of(orderItems).collect(Collectors.toSet());
    }

    public Order() {
    }

}
