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

    @DisplayName("Quando name é Null; Chama getAllIngredients")
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

    @DisplayName("Quando name não é Null; Chama getIngredientsByName")
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

    @DisplayName("Quando não é ID Null; Retorna ResponseEntity.ok(ingredient)")
    @Test
    void givenNotNullIDReturnIngredient() {

        // Given
        UUID notNullId = UUID.randomUUID();
        Ingredient mockedReturn = new Ingredient(UUID.randomUUID(), "Batata frita", 2, BigDecimal.TEN);

        when(ingredientService.getIngredientById(notNullId)).thenReturn(mockedReturn);

        // When
        ResponseEntity<Ingredient> result = ingredientController.getIngredientById(notNullId);

        // Then
        assertNotNull(notNullId);
        assertThat(result.getBody()).isEqualTo(mockedReturn);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(ingredientService).getIngredientById(notNullId);
    }

    @DisplayName("Quando ID é Null; Retorna ResponseEntity.notFound().build()")
    @Test
    void givenNullIDReturnIngredient() {

        // Given
        UUID nullId = null;

        // When
        ResponseEntity<Ingredient> result = ingredientController.getIngredientById(nullId);

        // Then
        assertThat(nullId).isNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        verify(ingredientService).getIngredientById(nullId);
    }
    @DisplayName("Quando request do name, quantity e price no formato correto, retorna id do Ingrediente criado")
    @Test
    void givenRightNameQuantityPriceCreateIngredient() {
        // Given
        String name = "Tomate";
        int quantity = 5;
        BigDecimal price = BigDecimal.TEN;
        UUID createdId = UUID.randomUUID();

        CreateIngredientRequest mockedReturn = new CreateIngredientRequest();
        mockedReturn.setName(name);
        mockedReturn.setQuantity(quantity);
        mockedReturn.setPrice(price);

        when(ingredientService.createIngredient(name, quantity, price)).thenReturn(createdId);

        // When
        ResponseEntity<UUID> result = ingredientController.createIngredient(mockedReturn);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(createdId);

        verify(ingredientService).createIngredient(name, quantity, price);
        verifyNoMoreInteractions(ingredientService);
    }
    @DisplayName("Quando request do name, quantity e price no formato e no id correto, atualiza ingrediente")
    @Test
    void updateIngredientIfRightIdAndRightInputs() {
        // Given
        String name = "Tomate";
        int quantity = 5;
        BigDecimal price = BigDecimal.TEN;
        UUID existingId = UUID.randomUUID();

        UpdateIngredientRequest mockedReturn = new UpdateIngredientRequest();
                mockedReturn.setName(name);
                mockedReturn.setQuantity(quantity);
                mockedReturn.setPrice(price);

        // When
        ResponseEntity<Void> result = ingredientController.updateIngredient(existingId, mockedReturn);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(ingredientService).updateIngredient(argThat(ingredient ->
                ingredient.getId().equals(existingId)
                        && ingredient.getName().equals(name)
                        && ingredient.getQuantity() == quantity
                        && ingredient.getPrice().equals(price)
        ));
        verifyNoMoreInteractions(ingredientService);
    }
    @DisplayName("Quando eu quero, delete ingrediente")
    @Test
    void deleteIngredientIfRightIdAndRightInputs() {
        // Given
        UUID existingId = UUID.randomUUID();

        // When

        ResponseEntity<Void> result = ingredientController.deleteIngredient(existingId);

        // Then

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(ingredientService).deleteIngredient(existingId);
        verifyNoMoreInteractions(ingredientService);
    }
}

    // TODO: PROXIMOS PASSOS:
    //  1. ELABORAR TODOS OS CENÁRIOS DOS MÉTODOS DO CONTROLLER
    //  2. DEFINIR GIVEN/WHEN/THEN
    //  3. MOCKAR RETORNO ESPERADO PARA O CENÁRIO ACONTECER
    //  4. RODAR OS TESTES E ATINGIR COVERAGE 100%

