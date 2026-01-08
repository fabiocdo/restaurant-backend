package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                    SELECT i.id, i.name, i.price, i.quantity
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
}
