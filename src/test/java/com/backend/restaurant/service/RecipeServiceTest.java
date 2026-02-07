package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.model.Recipe;
import com.backend.restaurant.repository.RecipeRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeRepositoryImpl recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @DisplayName("Given recipes in repository, when getAllRecipes is called, then return the full recipes list")
    @Test
    void callinggetAllRecipesReturnAllRecipes() {
        // Given
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
                "Omelete com Frango",
                listOfIngredients,
                BigDecimal.TEN
        );
        List<Recipe> mockedRepositoryReturn = List.of(mockedRecipe);

        when(recipeRepository.findAll()).thenReturn(mockedRepositoryReturn);

        // When
        List<Recipe> result = recipeService.getAllRecipes();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(mockedRecipe);

        verify(recipeRepository).findAll();
        verifyNoMoreInteractions(recipeRepository);
    }

    @DisplayName("Given null ID number, When getIngredientById is called, then throw IllegalArgumentException")
    @Test
    void getRecipeByIdId() {
        // Given
        UUID mockId = null;

        // When // Then
        assertThatThrownBy(() -> recipeService.getRecipeById(mockId)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(recipeRepository);
    }

    @DisplayName("Given empty ID number, When getIngredientById is called, Then if it is Empty return null")
    @Test
    void getRecipeByIdWhenIdReturnEmpty() {
        // Given
        UUID mockId = UUID.randomUUID();

        when(recipeRepository.findById(mockId)).thenReturn(Optional.empty());

        // When
        Recipe result = recipeService.getRecipeById(mockId);

        // Then
        assertThat(result).isNull();

        verifyNoMoreInteractions(recipeRepository);
    }

    @DisplayName("Given existing name, When calling getRecipesByName, Then return the Recipe")
    @Test
    void getRecipesByName() {
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
                listOfIngredients,
                BigDecimal.TEN
        );

        List<Recipe> mockedRepositoryReturn = List.of(mockedRecipe);


        when(recipeRepository.findByName(mockName)).thenReturn(mockedRepositoryReturn);

        // When
        List<Recipe> result = recipeService.getRecipesByName(mockName);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(mockName);

        verify(recipeRepository).findByName(mockName);
        verifyNoMoreInteractions(recipeRepository);
    }
    @DisplayName("Given null input name, When calling getRecipesByName, Then return the IllegalArgumentException")
    @Test
    void getNullGetRecipesByName() {
        // Given
        String nullMockName = null;

        // When // Then
        assertThatThrownBy(() -> recipeService.getRecipesByName(nullMockName)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(recipeRepository);
    }
}
