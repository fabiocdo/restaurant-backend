package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient getIngredientById(UUID id) {
        return null;
    }

    @Override
    public List<Ingredient> getIngredientsByName(String name) {
        return null;
    }

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
