package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeRowMapperTest {

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private RecipeRowMapper recipeRowMapper;

    @DisplayName("Given a ResultSet, when mapRow is called, then Recipe is correctly mapped")
    @Test
    void testRecipeRowMapper() throws Exception {
        // Given
        ResultSet rs = resultSet; //ResultSet is the JDBC type that represents a row returned from the database.

        UUID id = UUID.randomUUID();
        when(rs.getObject("id", UUID.class)).thenReturn(id);
        when(rs.getString("name")).thenReturn("Tomato");
        when(rs.getBigDecimal("total_price")).thenReturn(BigDecimal.TWO);

        // When
        Recipe recipe = recipeRowMapper.mapRow(rs, 1);

        // Then
        assertEquals(id, recipe.getId());
        assertEquals("Tomato", recipe.getName());
        assertEquals(BigDecimal.TWO, recipe.getTotalPrice());
    }
}
