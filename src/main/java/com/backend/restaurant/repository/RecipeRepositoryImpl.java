package com.backend.restaurant.repository;

import com.backend.restaurant.controller.RecipeController;
import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RecipeRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Recipe> findAll() {
        String recipesSql = "SELECT id, name, total_price \n" +
                "FROM recipe";

        String ingredientsSql = """
                    SELECT
                        i.id,
                        i.name,
                        i.price,
                        ir.ingredient_quantity AS quantity
                    FROM ingredients i
                    JOIN ingredient_recipe ir
                      ON i.id = ir.fk_ingredient_id
                    WHERE ir.fk_recipe_id = :recipeId
                """;


        List<Recipe> recipes =
                namedParameterJdbcTemplate.query(recipesSql, new RecipeRowMapper());

        recipes.forEach(recipe -> {

            MapSqlParameterSource params =
                    new MapSqlParameterSource()
                            .addValue("recipeId", recipe.getId());

            List<Ingredient> ingredientsForRecipe =
                    namedParameterJdbcTemplate.query(
                            ingredientsSql,
                            params,
                            new IngredientRowMapper()
                    );

            recipe.getIngredients().addAll(ingredientsForRecipe);
        });

        return recipes;
    }

    @Override
    public List<Recipe> findByName(String name) {

        String recipesSql = """
                    SELECT id, name, total_price
                    FROM recipe
                    WHERE name ILIKE :name
                """;

        String ingredientsSql = """
                    SELECT
                        i.id,
                        i.name,
                        i.price,
                        ir.ingredient_quantity AS quantity
                    FROM ingredients i
                    JOIN ingredient_recipe ir
                      ON i.id = ir.fk_ingredient_id
                    WHERE ir.fk_recipe_id = :recipeId
                """;

        MapSqlParameterSource recipeParams =
                new MapSqlParameterSource()
                        .addValue("name", "%" + name + "%");

        List<Recipe> recipes =
                namedParameterJdbcTemplate.query(
                        recipesSql,
                        recipeParams,
                        new RecipeRowMapper()
                );

        recipes.forEach(recipe -> {

            MapSqlParameterSource ingredientParams = new MapSqlParameterSource().addValue("recipeId", recipe.getId());

            List<Ingredient> ingredientsForRecipe =
                    namedParameterJdbcTemplate.query(
                            ingredientsSql,
                            ingredientParams,
                            new IngredientRowMapper()
                    );

            recipe.getIngredients().addAll(ingredientsForRecipe);
        });

        return recipes;
    }

    @Override
    public Optional<Recipe> findById(UUID id) {

        MapSqlParameterSource recipeParams = new MapSqlParameterSource().addValue("id", id);

        String recipesSql = """
                    SELECT id, name, total_price, created_date, last_modified_date
                    FROM recipe
                    WHERE id = :id
                """;

        MapSqlParameterSource ingredientParams = new MapSqlParameterSource().addValue("recipeId", id);

        String ingredientsSql = """
                    SELECT
                        i.id,
                        i.name,
                        i.price,
                        ir.ingredient_quantity AS quantity
                    FROM ingredients i
                    JOIN ingredient_recipe ir
                      ON i.id = ir.fk_ingredient_id
                    WHERE ir.fk_recipe_id = :recipeId
                """;

        try {

            Recipe recipe = namedParameterJdbcTemplate.queryForObject(
                    recipesSql,
                    recipeParams,
                    new RecipeRowMapper()
            );

            List<Ingredient> ingredients = namedParameterJdbcTemplate.query(
                    ingredientsSql,
                    ingredientParams,
                    new IngredientRowMapper()
            );

            recipe.getIngredients().addAll(ingredients);

            return Optional.of(recipe);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
