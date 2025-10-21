package com.backend.restaurant.controller;

import com.backend.restaurant.controller.dto.CreateIngredientRequest;
import com.backend.restaurant.controller.dto.UpdateIngredientRequest;
import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.service.IngredientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientServiceImpl ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @DisplayName("Given a null name, when calling getAllIngredients, should return a List with all ingredients")
    @Test
    void givenNullNameReturnListOfAllIngredients() {

        // Given
        String nullName = null;
        List<Ingredient> mockedReturn = List.of(
                new Ingredient(UUID.randomUUID(), "Ingredient Teste", 1, BigDecimal.ONE),
                new Ingredient(UUID.randomUUID(), "Ingredient Teste2", 2, BigDecimal.TEN));

        when(ingredientService.getAllIngredients()).thenReturn(mockedReturn);

        // When
        ResponseEntity<List<Ingredient>> result = ingredientController.getIngredients(nullName);

        // Then
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(ingredientService).getAllIngredients();
        verify(ingredientService, never()).getIngredientsByName(any());
    }

    @DisplayName("Given a empty name, when calling getAllIngredients, should return a List with all ingredients")
    @Test
    void givenEmptyNameReturnListOfAllIngredients() {

        // Given
        String emptyName = "      ";
        List<Ingredient> mockedReturn = List.of(
                new Ingredient(UUID.randomUUID(), "Ingredient Teste", 1, BigDecimal.ONE),
                new Ingredient(UUID.randomUUID(), "Ingredient Teste2", 2, BigDecimal.TEN));

        when(ingredientService.getAllIngredients()).thenReturn(mockedReturn);

        // When
        ResponseEntity<List<Ingredient>> result = ingredientController.getIngredients(emptyName);

        // Then
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(ingredientService).getAllIngredients();
        verify(ingredientService, never()).getIngredientsByName(any());
    }

    @DisplayName("Given a not null name, when calling getIngredientsByName, then return list of ingredients")
    @Test
    void givenNotNullNameReturnListOfIngredientsWithThatName() {

        // Given
        String validName = "Batata";
        List<Ingredient> mockedReturn = List.of(
                new Ingredient(UUID.randomUUID(), "Batata inglesa", 1, BigDecimal.ONE),
                new Ingredient(UUID.randomUUID(), "Batata frita", 2, BigDecimal.TEN));

        when(ingredientService.getIngredientsByName(validName)).thenReturn(mockedReturn);

        // When
        ResponseEntity<List<Ingredient>> result = ingredientController.getIngredients(validName);

        // Then
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(ingredientService, never()).getAllIngredients();
        verify(ingredientService).getIngredientsByName(validName);
    }

    @DisplayName("Given not null ID, when calling getIngredientById, then return ingredient")
    @Test
    void givenNotNullIDReturnIngredient() {

        // Given
        UUID notNullId = UUID.randomUUID();
        Ingredient mockedReturn = new Ingredient(notNullId, "Batata frita", 2, BigDecimal.TEN);

        when(ingredientService.getIngredientById(notNullId)).thenReturn(mockedReturn);

        // When
        ResponseEntity<Ingredient> result = ingredientController.getIngredientById(notNullId);

        // Then
        assertNotNull(result);
        assertThat(result.getBody()).isEqualTo(mockedReturn);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(ingredientService).getIngredientById(notNullId);
    }

    @DisplayName("Given null ID, when calling getIngredientById, return not found")
    @Test
    void givenNullIDReturnIngredient() {

        // Given
        UUID nullId = null;
        when(ingredientService.getIngredientById(nullId)).thenReturn(null);

        // When
        ResponseEntity<Ingredient> result = ingredientController.getIngredientById(nullId);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNull();

        verify(ingredientService).getIngredientById(nullId);
    }

    @DisplayName("Given right values to an ingredient, when calling createIngredient, then create and return HttpStatus.CREATED")
    @Test
    void givenRightNameQuantityPriceCreateIngredient() {
        // Given
        String name = "Tomate";
        int quantity = 5;
        BigDecimal price = BigDecimal.TEN;
        UUID createdId = UUID.randomUUID();

        CreateIngredientRequest mockedIngredient = new CreateIngredientRequest();
        mockedIngredient.setName(name);
        mockedIngredient.setQuantity(quantity);
        mockedIngredient.setPrice(price);

        when(ingredientService.createIngredient(name, quantity, price)).thenReturn(createdId);

        // When
        ResponseEntity<UUID> result = ingredientController.createIngredient(mockedIngredient);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(createdId);

        verify(ingredientService).createIngredient(name, quantity, price);
        verifyNoMoreInteractions(ingredientService);
    }

    @DisplayName("Given the correct type values to be updated, when calling updateIngredient, then update and return HttpStatus.NO_CONTENT")
    @Test
    void updateIngredientIfRightIdAndRightInputs() {
        // Given
        String name = "Tomate";
        int quantity = 5;
        BigDecimal price = BigDecimal.TEN;
        UUID existingId = UUID.randomUUID();

        UpdateIngredientRequest mockedIngredient = new UpdateIngredientRequest();
        mockedIngredient.setName(name);
        mockedIngredient.setQuantity(quantity);
        mockedIngredient.setPrice(price);

        Ingredient expectedIngredient = new Ingredient(
                existingId,
                mockedIngredient.getName(),
                mockedIngredient.getQuantity(),
                mockedIngredient.getPrice()
        );
        // When
        ResponseEntity<Void> result = ingredientController.updateIngredient(existingId, mockedIngredient);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();

        verify(ingredientService).updateIngredient(expectedIngredient);
        verifyNoMoreInteractions(ingredientService);
    }

    @DisplayName("Given valid ID, when calling deleteIngredient, then delete ingredient and return HttpStatus.NO_CONTENT")
    @Test
    void deleteIngredientIfRightIdAndRightInputs() {
        // Given
        UUID existingId = UUID.randomUUID();

        // When
        ResponseEntity<Void> result = ingredientController.deleteIngredient(existingId);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();

        verify(ingredientService).deleteIngredient(existingId);
        verifyNoMoreInteractions(ingredientService);
    }
}

