package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
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
        final String sql = "SELECT id, name, quantity, price, created_date, last_modified_date FROM ingredients WHERE id = :id";

        final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);

        try {
            final Ingredient ingredient = jdbcTemplate.queryForObject(sql, parameters, new IngredientRowMapper());
            return Optional.ofNullable(ingredient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
        Instant now = Instant.now();

        String sql = """    
        INSERT INTO ingredients (id, name, quantity, price, created_date, last_modified_date)
        VALUES (:id, :name, :quantity, :price, :createdDate, :lastModifiedDate)
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", ingredient.getId())
                .addValue("name", ingredient.getName())
                .addValue("quantity", ingredient.getQuantity())
                .addValue("price", ingredient.getPrice())
                .addValue("createdDate", Timestamp.from(now))
                .addValue("lastModifiedDate", Timestamp.from(now));

        jdbcTemplate.update(sql, params);
        return ingredient.getId();}

    @Override
    public void delete(UUID id) {

    }
}
