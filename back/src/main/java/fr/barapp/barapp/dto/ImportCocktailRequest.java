package fr.barapp.barapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/** Données pour importer un cocktail du catalogue externe dans la carte. */
public record ImportCocktailRequest(
        @NotBlank(message = "Le cocktail externe est obligatoire") String idExterne,
        @NotNull(message = "La catégorie est obligatoire") Long categorieId,
        @NotEmpty(message = "Au moins une taille est requise") List<@Valid TailleRequest> tailles) {
}
