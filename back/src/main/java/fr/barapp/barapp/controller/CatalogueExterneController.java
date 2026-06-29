package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.CocktailExterneDto;
import fr.barapp.barapp.dto.ImportCocktailRequest;
import fr.barapp.barapp.service.CatalogueExterneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Recherche et import de cocktails depuis le catalogue externe (réservé au barmaker). */
@RestController
@RequestMapping("/api/catalogue-externe")
public class CatalogueExterneController {

    private final CatalogueExterneService service;

    public CatalogueExterneController(CatalogueExterneService service) {
        this.service = service;
    }

    @GetMapping
    public List<CocktailExterneDto> rechercher(@RequestParam String q) {
        return service.rechercher(q);
    }

    @GetMapping("/suggestions")
    public List<CocktailExterneDto> suggestions() {
        return service.suggestions();
    }

    @PostMapping("/import")
    public ResponseEntity<CocktailDto> importer(@Valid @RequestBody ImportCocktailRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.importer(req));
    }
}
