package com.backend.restaurant.service;

import com.backend.restaurant.model.Ingredient;
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
    private IngredientRepositoryImpl ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @DisplayName("Given ingredients in repository, when getAllIngredients is called, then return the full ingredient list")
    @Test
    void callingGetAllIngredientsReturnAllIngredients() {
        // Given
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
    void callingExistingIdNumberReturnTheIdIngredient() {
        // Given
        UUID ingredientId = UUID.randomUUID();
        Ingredient mockIngredient = new Ingredient(ingredientId, "Tomato", 10, BigDecimal.TWO);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(mockIngredient));

        // When
        Ingredient result = ingredientService.getIngredientById(ingredientId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ingredientId);

        verify(ingredientRepository).findById(ingredientId);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given incorrect ID number, When getIngredientById is called, Then if it is Empty return null")
    @Test
    void getIngredientById_NullId() {
        // Given
        UUID mockId = null;

        // When // Then
        assertThatThrownBy(() -> ingredientService.getIngredientById(mockId)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given incorrect ID number, When getIngredientById is called, Then if it is Empty return null")
    @Test
    void getIngredientByIdWhenIdReturnNull() {
        // Given
        UUID mockId = UUID.randomUUID();

        when(ingredientRepository.findById(mockId)).thenReturn(Optional.empty());

        // When
        Ingredient result = ingredientService.getIngredientById(mockId);

        // Then
        assertThat(result).isNull();

        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given existing name, When calling getIngredientsByName, Then return the Ingredient")
    @Test
    void getIngredientsByName() {
        // Given
        String mockName = "Tomato";
        List<Ingredient> mockIngredient = List.of(
                new Ingredient(UUID.randomUUID(), mockName, 10, BigDecimal.TWO)
        );

        when(ingredientRepository.findByName(mockName)).thenReturn(mockIngredient);

        // When
        List<Ingredient> result = ingredientService.getIngredientsByName(mockName);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(mockName);


        verify(ingredientRepository).findByName(mockName);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given null input name, When calling getIngredientsByName, Then return the IllegalArgumentException")
    @Test
    void getNullIngredientsByName() {
        // Given
        String nullMockName = null;

        // When // Then
        assertThatThrownBy(() -> ingredientService.getIngredientsByName(nullMockName)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given existing ID, When calling updateIngredient, Then update the values of the Ingredient")
    @Test
    void updateExistingIngredient() {
        // Given
        UUID mockID = UUID.randomUUID();
        Ingredient mockIngredient = new Ingredient(mockID, "Tomato", 10, BigDecimal.TWO);

        when(ingredientRepository.findById(mockID)).thenReturn(Optional.of(mockIngredient));

        // When
        ingredientService.updateIngredient(mockIngredient);

        // Then

        verify(ingredientRepository).update(mockIngredient);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given null ID, When calling updateIngredient, Then throw ArgumentException")
    @Test
    void updateNullIngredient() {
        // Given
        Ingredient mockIngredient = new Ingredient(null, "null", 0, BigDecimal.ZERO);

        // When // Then
        assertThatThrownBy(() -> ingredientService.updateIngredient(mockIngredient)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given non-existent ID, When calling updateIngredient, Then throw NoSuchElementException")
    @Test
    void updateNonExistentIngredientThrows() {
        // Given
        UUID id = UUID.randomUUID();
        Ingredient ingredient = new Ingredient(id, "Lettuce", 5, BigDecimal.ONE);

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());

        // When // Then
        assertThatThrownBy(() -> ingredientService.updateIngredient(ingredient)).isInstanceOf(NoSuchElementException.class);

        verify(ingredientRepository).findById(id);
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given valid data, When createIngredient is called, Then repository.save is called and ID is returned")
    @Test
    void createIngredientSavesAndReturnsId() {
        // Given
        String mockString = "Tomato";
        int mockInt = 4;
        BigDecimal mockBigDecimal = BigDecimal.TEN;

        // When
        UUID id = ingredientService.createIngredient(mockString, mockInt, mockBigDecimal);

        // Then
        assertThat(id).isNotNull();

        verify(ingredientRepository).save(any(Ingredient.class));
        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given existing ID, When calling deleteIngredient, Then delete the ingredient")
    @Test
    void existingIdThenDeleteIngredient() {
        // Given
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
        UUID mockId = null;

        // When // Then
        assertThatThrownBy(() -> ingredientService.deleteIngredient(mockId)).isInstanceOf(IllegalArgumentException.class);

        verifyNoMoreInteractions(ingredientRepository);
    }

    @DisplayName("Given non-existent ID, When calling deleteIngredient, Then throw NoSuchElementException")
    @Test
    void deleteNonExistentIngredientThrows() {
        // Given
        UUID id = UUID.randomUUID();

        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());

        // When // Then
        assertThatThrownBy(() -> ingredientService.deleteIngredient(id)).isInstanceOf(NoSuchElementException.class);

        verify(ingredientRepository).findById(id);
        verifyNoMoreInteractions(ingredientRepository);
    }
}