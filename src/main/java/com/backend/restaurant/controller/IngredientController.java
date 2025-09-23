package com.backend.restaurant.controller;

import com.backend.restaurant.controller.dto.CreateIngredientRequest;
import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getIngredients(@RequestParam(value = "name", required = false) String name) {//required false, pq o true é default ai deixa de ser opcional

        if (name == null || name.isEmpty()) {
            final List<Ingredient> ingredients = ingredientService.getAllIngredients();
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
        final List<Ingredient> ingredients = ingredientService.getIngredientsByName(name);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UUID> getIngredientById(@PathVariable UUID id){
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createIngredient(
            @RequestBody CreateIngredientRequest request) {

        UUID id = UUID.randomUUID();

        Map<String, UUID> responseBody = new HashMap<>();
        responseBody.put("id", id);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}
