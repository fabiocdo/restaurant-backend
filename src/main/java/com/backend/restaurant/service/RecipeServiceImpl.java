package com.backend.restaurant.service;

import com.backend.restaurant.model.Recipe;
import com.backend.restaurant.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService{

private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {

        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Incorrect input-> name cannot be null");
        }

        return recipeRepository.findByName(name);
    }

    @Override
    public Recipe getRecipeById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }

        return recipeRepository.findById(id).orElse(null);
    }
}
