package com.backend.restaurant.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Recipe {
    private final UUID id;
    private final String name;
    private List<Ingredient> ingredients;
    private final BigDecimal totalPrice;

    public Recipe(UUID id, String name, List<Ingredient> ingredients, BigDecimal totalPrice) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.totalPrice = totalPrice;
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public List<Ingredient> getIngredients() {return ingredients;}
    public BigDecimal getTotalPrice() {return totalPrice;}
}
