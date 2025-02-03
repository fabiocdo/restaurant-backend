package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Override
    public List<Ingredient> getAllIngredients() {
        return Collections.emptyList();
    }

    @Override
    public Ingredient getIngredientById(UUID id) {
        return null;
    }

    @Override
    public List<Ingredient> getIngredientsByName(String name) {
        return null;    }

    @Override
    public void updateIngredient(Ingredient ingredient) {

    }

    @Override
    public UUID createIngredient(String name, int quantity, BigDecimal price) {
        return null;
    }

    @Override
    public void deleteIngredient(UUID id) {

    }
}
