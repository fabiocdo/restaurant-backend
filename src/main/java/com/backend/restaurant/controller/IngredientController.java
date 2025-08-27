package com.backend.restaurant.controller;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
        public ResponseEntity<List<Ingredient>> getIngredients(@RequestParam(value = "name", required = false) String name) {
            List<Ingredient> ingredients;//required false, pq o true é default ai deixa de ser opcional

            if (name == null || name.isEmpty()) {
                ingredients = ingredientService.getAllIngredients();
            } else {
                ingredients = ingredientService.getIngredientsByName(name);
            }
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }

    @GetMapping("/{id}")
    public ResponseEntity<UUID> getIngredientById(@PathVariable UUID id){
        UUID randomId = UUID.randomUUID();// enquanto não temos service/repository, devolvemos UUID random
        return new ResponseEntity<>(randomId, HttpStatus.OK);
    }
}
