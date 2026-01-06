package com.backend.restaurant.repository;

import com.backend.restaurant.model.Recipe;
import java.util.List;

public interface RecipeRepository {

    List<Recipe> findAll();
}
