package com.backend.restaurant.repository;

import com.backend.restaurant.model.Recipe;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository {

    List<Recipe> findAll();
    List<Recipe> findByName(String name);
    Optional<Recipe> findById(UUID id);

}
