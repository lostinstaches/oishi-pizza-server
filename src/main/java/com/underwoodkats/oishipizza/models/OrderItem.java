package com.underwoodkats.oishipizza.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne
    @JoinColumn
    private Order order;

    @ManyToOne
    @JoinColumn
    private Item item;

    @Column(name = "amount")
    private int amount;

    public OrderItem(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(order.getCustomerName(), that.order.getCustomerName())
                && Objects.equals(order.getAddress(), that.order.getAddress())
                && Objects.equals(order.getPhoneNumber(), that.order.getPhoneNumber())
                && Objects.equals(order.getComment(), order.getComment())
                && Objects.equals(order.getTotalItemPriceDollar(), order.getTotalItemPriceDollar())
                && Objects.equals(order.getDeliveryPriceDollar(), order.getDeliveryPriceDollar())
                && Objects.equals(order.getCreatedDate(), that.order.getCreatedDate())
                && Objects.equals(item.getTitle(), item.getTitle())
                && Objects.equals(item.getPriceDollar(), item.getPriceDollar())
                && Objects.equals(item.getType(), item.getType())
                && Objects.equals(item.getDescription(), item.getDescription())
                && Objects.equals(item.getImagePath(), item.getImagePath())
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                order.getCustomerName(),
                order.getAddress(),
                order.getPhoneNumber(),
                order.getComment(),
                order.getTotalItemPriceDollar(),
                order.getDeliveryPriceDollar(),
                order.getCreatedDate(),
                item.getTitle(),
                item.getPriceDollar(),
                item.getType(),
                item.getDescription(),
                item.getImagePath(),
                amount);
    }
}
