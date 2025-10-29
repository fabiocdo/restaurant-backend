package com.backend.restaurant.service;

import com.backend.restaurant.controller.dto.CreateIngredientRequest;
import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.repository.IngredientRepository;
import com.backend.restaurant.repository.IngredientRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientRepositoryImpl ingredientService;

    @InjectMocks
    private IngredientServiceImpl ingredientController;

    @DisplayName("Given ingredients in repository, when getAllIngredients is called, then return the full ingredient list")
    @Test
    void callingGetAllIngredientsReturnAllIngredients() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        List<Ingredient> mockIngredients = List.of(
                new Ingredient(UUID.randomUUID(), "Tomato", 10, BigDecimal.TWO),
                new Ingredient(UUID.randomUUID(), "Cheese", 5, BigDecimal.TEN),
                new Ingredient(UUID.randomUUID(), "Chicken", 3, BigDecimal.TEN)
        );

        when(ingredientRepository.findAll()).thenReturn(mockIngredients);

        // When
        List<Ingredient> result = ingredientService.getAllIngredients();

        // Then
        assertThat(result).hasSize(3);
        assertThat(result).containsAll(mockIngredients);

        verify(ingredientRepository).findAll();
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given ID number, When getIngredientById is called, Then Search and return the ID Ingredient called")
    @Test
    void callingExistingIdNumber() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        UUID ingredientId = UUID.fromString("8a5c1f3b-7e41-4df0-9c0e-5dbad1a2f4a7");
        Ingredient mockIngredient = new Ingredient(ingredientId, "Tomato", 10, BigDecimal.TWO);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(mockIngredient));

        // When
        Ingredient result = ingredientService.getIngredientById(ingredientId);

        // Then
        assertThat(result).isNotNull();

        verify(ingredientRepository).findById(ingredientId);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given incorrect ID number, When getIngredientById is called, Then if it is Empty return null")
    @Test
    void getIngredientById_NullId() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        // When
        Ingredient result = ingredientService.getIngredientById(null);

        // Then
        assertThat(result).isNull();

        verify(ingredientRepository).findById(null);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given existing name, When calling getIngredientsByName, Then return the Ingredient")
    @Test
    void getIngredientsByName() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        String mockName = "Tomato";
        List<Ingredient> mockIngredient = List.of(
                new Ingredient(UUID.randomUUID(), "Tomato", 10, BigDecimal.TWO)
        );

        when(ingredientRepository.findByName(mockName)).thenReturn(mockIngredient);

        // When
        List<Ingredient> result = ingredientService.getIngredientsByName(mockName);

        // Then
        assertThat(result).hasSize(1);

        verify(ingredientRepository).findByName("Tomato");
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given existing ID, When calling updateIngredient, Then update the values of the Ingredient")
    @Test
    void updateExistingIngredient() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        UUID mockID = UUID.randomUUID();
        Ingredient mockIngredient = new Ingredient(mockID, "Tomato", 10, BigDecimal.TWO);

        when(ingredientRepository.findById(mockID)).thenReturn(Optional.of(mockIngredient));

        // When
        ingredientService.updateIngredient(mockIngredient);

        // Then
        assertThat(mockIngredient).isNotNull();

        verify(ingredientRepository).update(mockIngredient);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given null ID, When calling updateIngredient, Then throw ArgumentException")
    @Test
    void updateNullIngredient() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        Ingredient mockIngredient = new Ingredient(null, "null", 0, BigDecimal.ZERO);

        // When
        //mockIngredient == null || id == null

        // Then
        assertThatThrownBy(() -> ingredientService.updateIngredient(mockIngredient)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given values, When calling createIngredient, Then return new Ingredient id")
    @Test
    void createIngredient() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        String name = "Tomato";
        int quantity = 5;
        BigDecimal price = BigDecimal.TEN;

        // When
        UUID result = ingredientService.createIngredient(name, quantity, price);

        // Then
        assertThat(result).isNotNull();

        verify(ingredientRepository, times(1))
                .save(argThat(ingredient ->
                        ingredient.getName().equals(name)
                                && ingredient.getQuantity() == quantity
                                && ingredient.getPrice().compareTo(price) == 0
                                && ingredient.getId() != null
                ));
        verifyNoMoreInteractions(ingredientRepository);
    }// achei esse bem complicado

    @DisplayName("Given existing ID, When calling deleteIngredient, Then delete the ingredient")
    @Test
    void existingIdThenDeleteIngredient() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        UUID mockId = UUID.randomUUID();
        Ingredient mockIngredient = new Ingredient(mockId, "Tomato", 10, BigDecimal.TEN);

        when(ingredientRepository.findById(mockId)).thenReturn(Optional.of(mockIngredient));

        // When
        ingredientService.deleteIngredient(mockId);

        // Then
        verify(ingredientRepository).delete(mockId);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given null ID, When calling deleteIngredient, Then throw new NoSuchElementException")
    @Test
    void nullIdThenThrowException() {
        // Given
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        IngredientServiceImpl ingredientService = new IngredientServiceImpl(ingredientRepository);

        UUID mockId = null;

        // When
        //mockId == null

        // Then
        assertThatThrownBy(() -> ingredientService.deleteIngredient(mockId)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(ingredientRepository);
    }
}