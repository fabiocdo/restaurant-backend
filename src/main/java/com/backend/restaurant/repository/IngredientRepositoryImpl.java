package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import java.util.Collections;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return Collections.emptyList();
    }

    @Override
    public Optional<Ingredient> findById(UUID id) {

            return Optional.empty();
    }

    @Override
    public List<Ingredient> findByName(String name) {
        final String sql = "SELECT id, name, quantity, price FROM ingredients WHERE name ILIKE CONCAT('%', :name, '%')";

        final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("name", name);

        return jdbcTemplate.query(sql, parameters, new IngredientRowMapper());
    }

    @Override
    public void update(Ingredient ingredient) {

    }

    @Override
    public UUID save(Ingredient ingredient) {
      System.out.println("teste");
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
