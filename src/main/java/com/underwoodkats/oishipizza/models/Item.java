package com.underwoodkats.oishipizza.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private Double priceDollar;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Item.Type type;

    private String description;

    private String imagePath;

    public enum Type {
        PIZZA,
        BEVERAGE,
        DESSERT,
        OTHER
    }
}
