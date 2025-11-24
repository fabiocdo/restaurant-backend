package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientRepositoryTest {
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private IngredientRepositoryImpl ingredientRepository;

    @DisplayName("Given ingredients in repository, when findAll is called, then return the full ingredient list")
    @Test
    void callingFindAllReturnAllIngredients() {

        // Given
        List<Ingredient> mockList = List.of(
                new Ingredient(UUID.randomUUID(), "Tomato", 10, BigDecimal.TWO),
                new Ingredient(UUID.randomUUID(), "Onion", 5, BigDecimal.ONE)
        );

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(IngredientRowMapper.class)
        )).thenReturn(mockList);

        // When
        List<Ingredient> result = ingredientRepository.findAll();

        // Then
        assertEquals(2, result.size());
        assertThat(mockList).isEqualTo(result);
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @DisplayName("Given none ingredients in repository, when findAll is called, then return the full ingredient list")
    @Test
    void callingFindAllReturnAEmptyIngredients() {

        //Given
        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(IngredientRowMapper.class)))
                .thenReturn(List.of());

        //When
        List<Ingredient> result = ingredientRepository.findAll();

        //Then
        assertThat(result).isEmpty();
    }

    @DisplayName("Given existing id in repository, when findById is called, then return the ingredient")
    @Test
    void callingFindById() {

        // Given
        UUID mockId = UUID.randomUUID();
        Ingredient mockIngredient = new Ingredient(mockId, "Tomato", 10, BigDecimal.TWO);

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(MapSqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenReturn(mockIngredient);

        // When
        Optional<Ingredient> result = ingredientRepository.findById(mockId);

        // Then
        assertThat(result).contains(mockIngredient);
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @DisplayName("Given non-existing id in repository, when findById is called, then return Optional.empty()")
    @Test
    void callingNonExistingFindById() {

        //Given
        UUID mockId = UUID.randomUUID();

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(MapSqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenThrow(new EmptyResultDataAccessException(0));

        // When
        Optional<Ingredient> result = ingredientRepository.findById(mockId);

        // Then
        assertThat(result).isEmpty();
    }

    @DisplayName("Given name in repository, when findByName is called, then return Ingredient")
    @Test
    void callingExistingName() {

        //Given
        String mockName = "Tomato";
        List<Ingredient> mockIngredient = List.of(new Ingredient(UUID.randomUUID(), mockName, 10, BigDecimal.TWO));

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(MapSqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenReturn(mockIngredient);

        // When
        List<Ingredient> result = ingredientRepository.findByName(mockName);

        // Then
        assertThat(result).isEqualTo(mockIngredient);
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @DisplayName("Given non-existing name in repository, when findByName is called, then return Empty list")
    @Test
    void callingNonExistingName() {

        //Given
        String nonExistentIngredientName = " ";
        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(MapSqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenReturn(List.of());

        //When
        List<Ingredient> result = ingredientRepository.findByName(nonExistentIngredientName);

        //Then
        assertThat(result).isEmpty();
        verify(namedParameterJdbcTemplate).query(anyString(), any(SqlParameterSource.class), any(RowMapper.class));
    }

    @DisplayName("Given id in repository, when update is called, then update Ingredient")
    @Test
    void callingUpdate() {
        // Given
        Ingredient ingredient = new Ingredient(UUID.randomUUID(), "Tomato", 10, BigDecimal.TWO);

        // When
        ingredientRepository.update(ingredient);

        // Then
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

    @DisplayName("Given values at input, when save is called, then return new id")
    @Test
    void callingSave() {
        // Given
        UUID mockId = UUID.randomUUID();
        Ingredient ingredient = new Ingredient(mockId, "Tomato", 10, BigDecimal.TWO);

        // When
        UUID returnedId = ingredientRepository.save(ingredient);

        // Then
        assertThat(mockId).isEqualTo(returnedId);
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

    @DisplayName("Given existing id, when delete is called, then delete ingredient")
    @Test
    void callingDelete() {
        // Given
        UUID mockId = UUID.randomUUID();

        // When
        ingredientRepository.delete(mockId);

        // Then
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

}