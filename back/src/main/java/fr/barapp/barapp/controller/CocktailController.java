package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.CocktailRequest;
import fr.barapp.barapp.service.CocktailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Points d'entrée REST pour les cocktails. */
@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {

    private final CocktailService service;

    public CocktailController(CocktailService service) {
        this.service = service;
    }

    /** Liste les cocktails. ?categorieId= filtre par catégorie ; ?duJour=true ne renvoie que la carte du jour. */
    @GetMapping
    public List<CocktailDto> lister(@RequestParam(required = false) Long categorieId,
                                    @RequestParam(required = false, defaultValue = "false") boolean duJour) {
        return service.lister(categorieId, duJour);
    }

    /** Active/désactive « servi » (barmaker). */
    @PatchMapping("/{id}/du-jour")
    public CocktailDto basculerDuJour(@PathVariable Long id) {
        return service.basculerDuJour(id);
    }

    /** Active/désactive « favori / mis en avant » (barmaker). */
    @PatchMapping("/{id}/favori")
    public CocktailDto basculerFavori(@PathVariable Long id) {
        return service.basculerFavori(id);
    }

    @GetMapping("/{id}")
    public CocktailDto trouver(@PathVariable Long id) {
        return service.trouver(id);
    }

    @PostMapping
    public ResponseEntity<CocktailDto> creer(@Valid @RequestBody CocktailRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(req));
    }

    @PutMapping("/{id}")
    public CocktailDto modifier(@PathVariable Long id, @Valid @RequestBody CocktailRequest req) {
        return service.modifier(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
