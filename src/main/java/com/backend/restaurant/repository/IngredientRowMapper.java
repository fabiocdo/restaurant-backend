package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class IngredientRowMapper implements RowMapper<Ingredient> {
    @Override
    public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
        final UUID id = rs.getObject("id", UUID.class);
        final String name = rs.getString("name");
        final BigDecimal price = rs.getBigDecimal("price");
        final int quantity = rs.getInt("quantity");

        return new Ingredient(id, name, quantity, price);
    }
}