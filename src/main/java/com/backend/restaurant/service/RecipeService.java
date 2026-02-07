package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
import java.util.List;
import java.util.UUID;

public interface RecipeService {

    List<Recipe> getAllRecipes();
    List<Recipe> getRecipesByName(String name);
    Recipe getRecipeById(UUID id);
}
