package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.IngredientDto;
import fr.barapp.barapp.dto.IngredientRequest;
import fr.barapp.barapp.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Points d'entrée REST pour les ingrédients. */
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<IngredientDto> lister() {
        return service.lister();
    }

    @PostMapping
    public ResponseEntity<IngredientDto> creer(@Valid @RequestBody IngredientRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(req));
    }

    @PutMapping("/{id}")
    public IngredientDto modifier(@PathVariable Long id, @Valid @RequestBody IngredientRequest req) {
        return service.modifier(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
