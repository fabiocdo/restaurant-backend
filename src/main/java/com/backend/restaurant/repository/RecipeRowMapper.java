package com.backend.restaurant.repository;

import com.backend.restaurant.model.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class RecipeRowMapper implements RowMapper<Recipe> {
    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
        final UUID id = rs.getObject("id", UUID.class);;
        final String name = rs.getString("name");
        final BigDecimal totalPrice = rs.getBigDecimal("total_price");

        return new Recipe(id, name, new ArrayList<>(), totalPrice);
    }
}
