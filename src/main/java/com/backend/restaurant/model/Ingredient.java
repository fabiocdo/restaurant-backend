package com.backend.restaurant.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Ingredient {

    private final UUID id;
    private final String name;
    private final int quantity;
    private final BigDecimal price;


    public Ingredient(UUID id, String name, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
