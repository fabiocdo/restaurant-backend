package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public IngredientRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, quantity, price, created_date, last_modified_date FROM ingredients";

        return namedParameterJdbcTemplate.query(sql, new IngredientRowMapper());
    }

    @Override
    public Optional<Ingredient> findById(UUID id) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);

        final String sql = "SELECT id, name, quantity, price, created_date, last_modified_date FROM ingredients WHERE id = :id";

        try {
            final Ingredient ingredient = namedParameterJdbcTemplate.queryForObject(sql, parameters, new IngredientRowMapper());
            return Optional.ofNullable(ingredient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Ingredient> findByName(String name) {

        final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("name", name);

        final String sql = "SELECT id, name, quantity, price FROM ingredients WHERE name ILIKE CONCAT('%', :name, '%')";

        return namedParameterJdbcTemplate.query(sql, parameters, new IngredientRowMapper());
    }

    @Override
    public void update(Ingredient ingredient) {
        Instant now = Instant.now();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", ingredient.getId())
                .addValue("name", ingredient.getName())
                .addValue("quantity", ingredient.getQuantity())
                .addValue("price", ingredient.getPrice())
                .addValue("lastModifiedDate", Timestamp.from(now));

        String sql = """
        UPDATE ingredients
        SET name = :name,
            quantity = :quantity,
            price = :price,
            last_modified_date = :lastModifiedDate
        WHERE id = :id
    """;

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public UUID save(Ingredient ingredient) {
        Instant now = Instant.now();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", ingredient.getId())
                .addValue("name", ingredient.getName())
                .addValue("quantity", ingredient.getQuantity())
                .addValue("price", ingredient.getPrice())
                .addValue("createdDate", Timestamp.from(now))
                .addValue("lastModifiedDate", Timestamp.from(now));

        String sql = """    
        INSERT INTO ingredients (id, name, quantity, price, created_date, last_modified_date)
        VALUES (:id, :name, :quantity, :price, :createdDate, :lastModifiedDate)
    """;

        namedParameterJdbcTemplate.update(sql, params);
        return ingredient.getId();}

    @Override
    public void delete(UUID id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", id);

        String sql = """
           DELETE FROM ingredients WHERE id = :id;
           """;

        namedParameterJdbcTemplate.update(sql, params);
    }
}
