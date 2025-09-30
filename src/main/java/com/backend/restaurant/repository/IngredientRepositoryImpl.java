package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IngredientRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, quantity, price, created_date, last_modified_date FROM ingredients";

        return jdbcTemplate.query(sql, new IngredientRowMapper());
    }

    @Override
    public Ingredient findById(UUID id) {
        return null;
    }

    @Override
    public List<Ingredient> findByName(String name) {
        return List.of();
    }

    @Override
    public void update(Ingredient ingredient) {

    }

    @Override
    public UUID save(Ingredient ingredient) {
        LocalDateTime now = LocalDateTime.now();

        String sql = """    
        INSERT INTO ingredients (id, name, quantity, price, created_date, last_modified_date)
        VALUES (:id, :name, :quantity, :price, :createdDate, :lastModifiedDate)
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", ingredient.getId())
                .addValue("name", ingredient.getName())
                .addValue("quantity", ingredient.getQuantity())
                .addValue("price", ingredient.getPrice())
                .addValue("createdDate", now)
                .addValue("lastModifiedDate", now);

        jdbcTemplate.update(sql, params);
        return ingredient.getId();}

    @Override
    public void delete(UUID id) {

    }
}
