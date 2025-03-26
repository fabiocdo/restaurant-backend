package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
    //    private static final String UUID = "id";
//    private static final String name = "name";
//    private static final String quantity = "quantity";
//    private static final String price = "price";
//    private static final String created_date = "created_date";
//    private static final String last_modified_date = "last_modified_date";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IngredientRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//n entendi esse construtor fabo NamedParameterJdbcTemplate

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
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
