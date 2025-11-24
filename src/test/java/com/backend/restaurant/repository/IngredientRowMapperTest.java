package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class IngredientRowMapperTest {

        @Mock
        private ResultSet resultSet;

        @InjectMocks
        private IngredientRowMapper ingredientRowMapper;

    @DisplayName("Given a ResultSet, when mapRow is called, then Ingredient is correctly mapped")
    @Test
    void testIngredientRowMapper() throws Exception {
        // Given
        ResultSet rs = resultSet; //ResultSet is the JDBC type that represents a row returned from the database.

        UUID id = UUID.randomUUID();
        when(rs.getObject("id", UUID.class)).thenReturn(id);
        when(rs.getString("name")).thenReturn("Tomato");
        when(rs.getInt("quantity")).thenReturn(10);
        when(rs.getBigDecimal("price")).thenReturn(BigDecimal.TWO);

        // When
        Ingredient ingredient = ingredientRowMapper.mapRow(rs,1);

        // Then
        assertEquals(id, ingredient.getId());
        assertEquals("Tomato", ingredient.getName());
        assertEquals(10, ingredient.getQuantity());
        assertEquals(BigDecimal.TWO, ingredient.getPrice());
    }
}
