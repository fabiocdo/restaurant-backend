package com.backend.restaurant.controller;

import com.backend.restaurant.model.Ingredient;
import com.backend.restaurant.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;//esse

import java.util.List;
import java.util.UUID;//esse
import java.util.Collections;//esse

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/getAllIngredients") //mudei pra chamar todos, sem esse caminho nao consegui fazer funcionar
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
    @GetMapping(params = "id")
    public ResponseEntity<UUID> getIngredientById(@RequestParam UUID id) {
        UUID randomId = UUID.randomUUID();// enquanto não temos service/repository, devolvemos UUID random
        return new ResponseEntity<>(randomId, HttpStatus.OK);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Ingredient>> getIngredientByName(@RequestParam String name) {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        // enquanto não temos service/repository, devolvemos lista vazia emptyList()
    }
}
