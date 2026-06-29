package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CategorieDto;
import fr.barapp.barapp.dto.CategorieRequest;
import fr.barapp.barapp.service.CategorieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Points d'entrée REST pour les catégories. */
@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieService service;

    public CategorieController(CategorieService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategorieDto> lister() {
        return service.lister();
    }

    @GetMapping("/{id}")
    public CategorieDto trouver(@PathVariable Long id) {
        return service.trouver(id);
    }

    @PostMapping
    public ResponseEntity<CategorieDto> creer(@Valid @RequestBody CategorieRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(req));
    }

    @PutMapping("/{id}")
    public CategorieDto modifier(@PathVariable Long id, @Valid @RequestBody CategorieRequest req) {
        return service.modifier(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
