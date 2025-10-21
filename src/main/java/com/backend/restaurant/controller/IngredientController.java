package com.backend.restaurant.controller;

import com.backend.restaurant.controller.dto.CreateIngredientRequest;
import com.backend.restaurant.controller.dto.UpdateIngredientRequest;
import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getIngredients(@RequestParam(value = "name", required = false) String name) {//required false, pq o true é default ai deixa de ser opcional

        if (name == null || name.isBlank()) {
            final List<Ingredient> ingredients = ingredientService.getAllIngredients();
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
        final List<Ingredient> ingredients = ingredientService.getIngredientsByName(name);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable UUID id){
        Ingredient ingredient = ingredientService.getIngredientById(id);

        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    public ResponseEntity<UUID> createIngredient(@RequestBody CreateIngredientRequest request) {

        UUID id = ingredientService.createIngredient(
                request.getName(),
                request.getQuantity(),
                request.getPrice()
        );

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIngredient(@PathVariable UUID id, @RequestBody UpdateIngredientRequest request){

        Ingredient ingredient = new Ingredient(
                id,
                request.getName(),
                request.getQuantity(),
                request.getPrice()
        );
        ingredientService.updateIngredient(ingredient);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable UUID id) {

        ingredientService.deleteIngredient(id);

        return ResponseEntity.noContent().build();
    }
}
