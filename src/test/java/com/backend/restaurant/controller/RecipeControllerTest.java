package com.backend.restaurant.controller;

import com.backend.restaurant.model.Recipe;
import com.backend.restaurant.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private  RecipeController recipeController;

    @DisplayName("Given a null name, when calling getAllRecipes, should return a List with all recipes")
    @Test
    void givenNullNameReturnListOfAllRecipes() {
        //GIVEN
        String validName = null;
        List<Recipe> mockedReturn = List.of(
                new Recipe(UUID.randomUUID(), "Tomato Pasta", Collections.emptyList(), BigDecimal.ONE),
                new Recipe(UUID.randomUUID(), "Omelete de Bacon", Collections.emptyList(), BigDecimal.ONE));

        when(recipeService.getAllRecipes()).thenReturn(mockedReturn);

        //WHEN
        ResponseEntity<List<Recipe>> result = recipeController.getAllRecipes(validName);

        //THEN
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(recipeService).getAllRecipes();
        verify(recipeService, never()).getRecipesByName(any());
    }

    @DisplayName("Given a empty name, when calling getAllRecipes, should return a List with all recipes")
    @Test
    void givenEmptyNameReturnListOfAllRecipes() {
        //GIVEN
        String validName = "      ";
        List<Recipe> mockedReturn = List.of(
                new Recipe(UUID.randomUUID(), "Tomato Pasta", Collections.emptyList(), BigDecimal.ONE),
                new Recipe(UUID.randomUUID(), "Omelete de Bacon", Collections.emptyList(), BigDecimal.ONE));

        when(recipeService.getAllRecipes()).thenReturn(mockedReturn);

        //WHEN
        ResponseEntity<List<Recipe>> result = recipeController.getAllRecipes(validName);

        //THEN
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(recipeService).getAllRecipes();
        verify(recipeService, never()).getRecipesByName(any());
    }

    @DisplayName("Given a not null name, when calling getRecipesByName, then return list of recipes")
    @Test
    void givenNotNullNameReturnListOfRecipesWithThatName(){
        //GIVEN
        String validName = "Tomato Pasta";
        List<Recipe> mockedReturn = List.of(
                new Recipe(UUID.randomUUID(), "Tomato Pasta", Collections.emptyList(), BigDecimal.ONE));

        when(recipeService.getRecipesByName(validName)).thenReturn(mockedReturn);

        //WHEN
        ResponseEntity<List<Recipe>> result = recipeController.getAllRecipes(validName);

        //THEN
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(recipeService, never()).getAllRecipes();
        verify(recipeService).getRecipesByName(validName);
    }
}
