package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
    public Optional<Ingredient> findById(UUID id) {
        String sql = "SELECT id, name, quantity, price, created_date, last_modified_date " + "FROM ingredients WHERE id = :id";

        final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);

        List<Ingredient> results = jdbcTemplate.query(sql, parameters, new IngredientRowMapper());

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Ingredient> findByName(String name) {
        String sql = "SELECT id, name, quantity, price " + "FROM ingredients " + "WHERE name LIKE :name";

        final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("name", "%" + name + "%");

        return jdbcTemplate.query(sql, parameters, new IngredientRowMapper());
    }

    @Override
    public void update(Ingredient ingredient) {

    }

    @Override
    public UUID save(Ingredient ingredient) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
