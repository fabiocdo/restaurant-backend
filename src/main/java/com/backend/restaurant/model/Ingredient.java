package com.backend.restaurant.model;

import java.math.BigDecimal;
import java.util.Objects;
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

    public UUID getId() {return id;}

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, price);
    }

}
