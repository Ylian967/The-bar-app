package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CommandeDto;
import fr.barapp.barapp.dto.CommandeRequest;
import fr.barapp.barapp.service.CommandeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Points d'entrée REST pour les commandes. */
@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    /** Un client passe une commande. */
    @PostMapping
    public ResponseEntity<CommandeDto> passer(@Valid @RequestBody CommandeRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.passer(req));
    }

    @GetMapping("/{id}")
    public CommandeDto trouver(@PathVariable Long id) {
        return service.trouver(id);
    }

    /** Liste des commandes à traiter (barman). */
    @GetMapping("/a-traiter")
    public List<CommandeDto> aTraiter() {
        return service.aTraiter();
    }

    /** « Mes commandes » d'un client (par prénom / table). */
    @GetMapping
    public List<CommandeDto> parClient(@RequestParam String clientNom) {
        return service.parClient(clientNom);
    }

    /** Le barman fait avancer un cocktail d'une étape. */
    @PatchMapping("/{commandeId}/lignes/{ligneId}/avancer")
    public CommandeDto avancer(@PathVariable Long commandeId, @PathVariable Long ligneId) {
        return service.avancerLigne(commandeId, ligneId);
    }
}
