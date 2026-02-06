package com.backend.restaurant.controller;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
import com.backend.restaurant.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            final List<Recipe> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes);
        }
        final List<Recipe> recipes = recipeService.getRecipesByName(name);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable UUID id){
        Recipe recipe = recipeService.getRecipeById(id);

        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}
