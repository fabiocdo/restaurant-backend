package com.backend.restaurant.service;

import com.backend.restaurant.model.Recipe;
import com.backend.restaurant.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
