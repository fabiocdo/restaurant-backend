package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ingredient> getIngredientsByName(String name) {
        return ingredientRepository.findByName(name);
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {

        Ingredient existing = getIngredientById(ingredient.getId());

        if (existing==null) {
            throw new NoSuchElementException("Ingredient with id " + ingredient.getId() + " doesn't exist.");
        }
        ingredientRepository.update(ingredient);
    }
    @Override
    public UUID createIngredient(String name, int quantity, BigDecimal price) {
        UUID id = UUID.randomUUID();
        Ingredient ingredient = new Ingredient(id, name, quantity, price);
        ingredientRepository.save(ingredient);

        return id;
    }

    @Override
    public void deleteIngredient(UUID id) {
        ingredientRepository.delete(id);
    }
}
