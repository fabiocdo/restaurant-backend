package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IngredientService {

    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(UUID id);

    List<Ingredient> getIngredientsByName(String name);

    void updateIngredient(Ingredient ingredient);

    UUID createIngredient(String name, int quantity, BigDecimal price);

    void deleteIngredient(UUID id);
}
