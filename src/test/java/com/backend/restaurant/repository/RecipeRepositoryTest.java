package com.backend.restaurant.repository;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeRepositoryTest {
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private RecipeRepositoryImpl recipeRepository;

    @DisplayName("Given recipes in repository, when findAll is called, then return the full recipes list")
    @Test
    void callingFindAllReturnAllRecipes() {

        // Given
        String mockName = "Omelete com Frango";
        UUID randomId = UUID.randomUUID();
        List<Ingredient> listOfIngredients = List.of(
                new Ingredient(
                        UUID.randomUUID(),
                        "Ovo",
                        2,
                        BigDecimal.TEN
                ),
                new Ingredient(
                        UUID.randomUUID(),
                        "Frango",
                        2,
                        BigDecimal.TEN
                )
        );

        Recipe mockedRecipe = new Recipe(
                randomId,
                mockName,
                new ArrayList<>(),
                BigDecimal.TEN
        );

        List<Recipe> mockedRepositoryReturn = List.of(mockedRecipe);

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(RecipeRowMapper.class)
        )).thenReturn(mockedRepositoryReturn);

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(SqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenReturn(listOfIngredients);


        // When
        List<Recipe> result = recipeRepository.findAll();

        // Then
        assertEquals(1, result.size());

        Recipe resultRecipe = result.get(0);
        assertEquals("Omelete com Frango", resultRecipe.getName());
        assertEquals(2, resultRecipe.getIngredients().size());

        verify(namedParameterJdbcTemplate).query(
                anyString(),
                any(RecipeRowMapper.class)
        );

        verify(namedParameterJdbcTemplate).query(
                anyString(),
                any(SqlParameterSource.class),
                any(IngredientRowMapper.class)
        );

        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }
    @DisplayName("Given none recipes in repository, when findAll is called, then return empty recipes")
    @Test
    void callingFindAllReturnAEmptyRecipes() {

        //Given
        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(RecipeRowMapper.class)))
                .thenReturn(List.of());

        //When
        List<Recipe> result = recipeRepository.findAll();

        //Then
        assertThat(result).isEmpty();
    }
    @DisplayName("Given name in repository, when findByName is called, then return Recipe")
    @Test
    void callingExistingName() {

        // Given
        String mockName = "Omelete com Frango";
        UUID randomId = UUID.randomUUID();
        List<Ingredient> listOfIngredients = List.of(
                new Ingredient(
                        UUID.randomUUID(),
                        "Ovo",
                        2,
                        BigDecimal.TEN
                ),
                new Ingredient(
                        UUID.randomUUID(),
                        "Frango",
                        2,
                        BigDecimal.TEN
                )
        );

        Recipe mockedRecipe = new Recipe(
                randomId,
                mockName,
                new ArrayList<>(),
                BigDecimal.TEN
        );

        List<Recipe> mockedRepositoryReturn = List.of(mockedRecipe);

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(SqlParameterSource.class),
                any(RecipeRowMapper.class)
        )).thenReturn(mockedRepositoryReturn);

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(SqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenReturn(listOfIngredients);

        // When
        List<Recipe> result = recipeRepository.findByName(mockName);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(mockName);

        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }
    @DisplayName("Given non-existing name in repository, when findByName is called, then return Empty list")
    @Test
    void callingNonExistingName() {

        //Given
        String nonExistentRecipeName = " ";
        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(MapSqlParameterSource.class),
                any(RecipeRowMapper.class)
        )).thenReturn(List.of());

        //When
        List<Recipe> result = recipeRepository.findByName(nonExistentRecipeName);

        //Then
        assertThat(result).isEmpty();
        verify(namedParameterJdbcTemplate).query(anyString(), any(SqlParameterSource.class), any(RowMapper.class));
    }

    @DisplayName("Given existing ID in repository, when findById is called, then return the recipe")
    @Test
    void callingFindById() {

        // Given
        String mockName = "Omelete com Frango";
        UUID calledId = UUID.randomUUID();
        List<Ingredient> listOfIngredients = List.of(
                new Ingredient(
                        UUID.randomUUID(),
                        "Ovo",
                        2,
                        BigDecimal.TEN
                ),
                new Ingredient(
                        UUID.randomUUID(),
                        "Frango",
                        2,
                        BigDecimal.TEN
                )
        );

        Recipe mockedRecipe = new Recipe(
                calledId,
                mockName,
                new ArrayList<>(),
                BigDecimal.TEN
        );

        when(namedParameterJdbcTemplate.queryForObject(
                anyString(),
                any(SqlParameterSource.class),
                any(RecipeRowMapper.class)
        )).thenReturn(mockedRecipe);

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(SqlParameterSource.class),
                any(IngredientRowMapper.class)
        )).thenReturn(listOfIngredients);

        // When
        Optional<Recipe> result = recipeRepository.findById(calledId);

        // Then
        assertThat(result).contains(mockedRecipe);
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
                any(RecipeRowMapper.class)
        )).thenThrow(new EmptyResultDataAccessException(0));

        // When
        Optional<Recipe> result = recipeRepository.findById(mockId);

        // Then
        assertThat(result).isEmpty();
    }
}

