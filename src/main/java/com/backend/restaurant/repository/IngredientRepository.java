package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;

import java.util.List;
import java.util.UUID;

public interface IngredientRepository {

    List<Ingredient> findAll();

    Ingredient findById(UUID id);

    List<Ingredient> findByName(String name);

    void update(Ingredient ingredient);

    UUID save(Ingredient ingredient);

    void delete(UUID id);
}
